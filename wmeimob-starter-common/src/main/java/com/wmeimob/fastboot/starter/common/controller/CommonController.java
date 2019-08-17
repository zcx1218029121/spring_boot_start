package com.wmeimob.fastboot.starter.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.wmeimob.fastboot.autoconfigure.oss.AliyunOss;
import com.wmeimob.fastboot.autoconfigure.oss.AliyunOssProperties;
import com.wmeimob.fastboot.autoconfigure.qiniu.QiNiu;
import com.wmeimob.fastboot.autoconfigure.qiniu.QiNiuProperties;
import com.wmeimob.fastboot.autoconfigure.sms.aliyun.AliyunSms;
import com.wmeimob.fastboot.autoconfigure.sms.aliyun.AliyunSmsProperties;
import com.wmeimob.fastboot.autoconfigure.upload.UploadFileProperies;
import com.wmeimob.fastboot.core.exception.CustomException;
import com.wmeimob.fastboot.core.rest.RestResult;
import com.wmeimob.fastboot.starter.common.entity.RichText;
import com.wmeimob.fastboot.starter.common.service.RichTextService;
import com.wmeimob.fastboot.starter.common.sms.SmsSendHandler;
import com.wmeimob.fastboot.util.InputValidator;
import com.wmeimob.fastboot.util.RandomCodeUtil;
import com.wmeimob.fastboot.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping({"common"})
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);
    @Resource
    private QiNiuProperties qiNiuProperties;

    @Resource
    private AliyunOssProperties aliyunOssProperties;

    @Resource
    private AliyunSmsProperties aliyunSmsProperties;

    @Resource
    private SmsSendHandler smsSendHandler;

    @Resource
    private UploadFileProperies uploadFileProperies;
    @Resource(
            name = "richTextCommonServiceImpl"
    )

    private RichTextService richTextCommonServiceImpl;
    private static final String REG_SCENE = "reg";

    public CommonController() {

    }

    /**
     * 返回一切富文本
     *
     * @param id id
     * @return 返回查询的 富文本
     */
    @GetMapping({"rich-text/{id}"})
    public RichText getRichTextById(@PathVariable("id") Integer id) {
        RichText richText = richTextCommonServiceImpl.securityGet(id, (dataId) -> true);
        return richText == null ? new RichText() : richText;
    }

    /**
     * 获取QINIU 云的token
     *
     * @return map对象
     */
    @GetMapping({"qiniu-token"})
    public Map<String, String> getQiniuToken() {
        QiNiu qiNiu = new QiNiu(this.qiNiuProperties);
        String token = qiNiu.getUploadToken();
        Map<String, String> map = new HashMap<>();
        map.put("uploadToken", token);
        map.put("domain", this.qiNiuProperties.getDomain());
        return map;
    }

    /**
     * 阿里云 oss上传
     *
     * @return 阿里云oss上传的token
     */
    @GetMapping({"oss-token"})
    public Map<String, String> ossToken() {
        return AliyunOss.getInstance(aliyunOssProperties).uploadParameters();
    }

    /**
     * 上传操作 实际上用的七牛云前端上传
     *
     * @param multipartFile 上传的文件
     * @param scene         场景
     * @return 返回 上传结果
     */
    @PostMapping({"upload"})
    public String upload(@RequestParam("file") MultipartFile multipartFile, String scene) {
        UploadFileProperies.UploadFileConfig config = this.uploadFileProperies.getConfigs().get(scene);
        if (config == null) {
            throw new CustomException("can not found scene config");
        } else {
            File file = new File(config.getDir());
            if (!file.exists()) {
                boolean flagOFMkdirs = file.mkdirs();
                if (!flagOFMkdirs) {
                    throw new CustomException("创建目录失败");
                }
            }

            String fileName = RandomCodeUtil.randCode(32);
            byte[] buff = new byte[4096];

            try {
                FileOutputStream output = new FileOutputStream(config.getDir() + fileName);
                Throwable throwable = null;

                try {
                    InputStream input = multipartFile.getInputStream();
                    // the var is never use
                    // boolean b = true;
                    int byteCount;
                    while ((byteCount = input.read(buff, 0, 4096)) != -1) {
                        output.write(buff, 0, byteCount);
                    }

                } catch (Throwable throwable1) {
                    throwable = throwable1;
                    throw throwable1;
                } finally {
                    if (throwable != null) {
                        try {
                            output.close();
                        } catch (Throwable throwable1) {
                            throwable.addSuppressed(throwable1);
                        }
                    } else {
                        output.close();
                    }

                }
            } catch (IOException e) {
                log.error("create file io exception");
            }

            return (config.isSecure() ? "https" : "http") + "://" + config.getDomain() + (StringUtils.isEmpty(config.getVisitPrefix()) ? "" : config.getVisitPrefix()) + "/" + fileName;
        }


    }

    @GetMapping({"reg-sms"})
    public RestResult sendRegSms(String mobile) {
        if (InputValidator.isMobile(mobile)) {
            throw new CustomException("手机号码格式错误");
        } else {
            String code = RandomCodeUtil.randCodeNum(this.aliyunSmsProperties.getConfigs().get("reg").getLength());
            this.smsSendHandler.process(mobile, code, REG_SCENE, this.aliyunSmsProperties);
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("code", code);


            try {
                // 短消息发送 异常会throw clientException 异常
                SendSmsResponse resp = AliyunSms.getInstance(aliyunSmsProperties).sendSms(mobile, paramMap, REG_SCENE);
                log.info(JSONObject.toJSONString(resp));
            } catch (ClientException e) {
                log.error(e.getErrMsg(), e);
            }
        }
        return RestResult.success();
    }
}