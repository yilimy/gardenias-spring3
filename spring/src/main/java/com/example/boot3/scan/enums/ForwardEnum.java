package com.example.boot3.scan.enums;

import com.example.boot3.scan.integration.log.CodeFunctionInterface;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum ForwardEnum implements CodeFunctionInterface {

    /**
     * 命名规则：
     * 前两位为服务编号，如：calculate服务->10，seal服务->20
     * 中间两位为service编号，如：calculate服务->1001，seal服务->2001
     * api-hub --> 4001
     * 后两位为method编号，如下所示。
     */
    CALCULATE_QUERY_SEAL("calculateService","sealInfo", "100001"),

    CALCULATE_ADD_SEAL("calculateService", "saveSealInfo", "100002"),
    //CALCULATE_ADD_SEAL_IMPL("com.gomain.api.calculate.service.impl.CalculateServiceImpl", "sealInfo", "100002");

    SYNC_ENTERPRISE("syncService","syncEnterprise", "300101"),
    SYNC_INFO("syncService","syncInfo", "300102"),
    SYNC_DEPT_MESSAGE("SyncDeptMessageService", "syncCorporateMessage","300103"),

    // api-hub
    // 获取印章列表二维码
    QR_CODE_SEAL_APPLY("qrCodeService", "sealApplyQrCode","400101"),
    // 推送二维码扫描结果
    QR_CODE_SCAN_RESULT("qrCodeService", "scanQrCodeResult","400102"),
    // 查询二维码扫描结果
    QR_CODE_FIND_DATA("qrCodeService", "findQrCodeData","400103"),
    // 创建签章任务
    QR_CODE_CREATE_MISSION("qrCodeService", "createStampMission","400104"),
    QR_SEAL_QUERY("manageService", "qrySealData","400105"),
    // log
    // 扫码签章，保存签章日志
    QR_SAVE_STAMP_LOG("qrCodeService", "saveStampLog","500104"),
    /*
     * 保存UKey签章日志，
     * 相同的请求码，在访问省枢纽和省市州时，有不同的逻辑，
     * 发起访问方(SDK前置服务)没有区别
     */
    SAVE_STAMP_UKEY_LOG("logService", "saveStampUkLog","500106"),
    // 保存云签章签章日志
    SAVE_STAMP_DEFAULT_LOG("logService", "saveStampDefaultLog","500107"),
    // 查询订单状态
    QR_FIND_QR_CODE_STATUS("qrCodeService", "findQrCodeStatus","500109"),
    // 获取签名值
    QR_FIND_SIGN_VALUE("qrCodeService", "findSignValue","500110"),
    SIGN_DIGEST_SUB("frontSdkService", "signDigest","600103"),
    PROV_SEAL_AUTHED("qrCodeService", "provSealAuthed4Forward","400109"),
    ;

    private String code;

    private String service;

    @Getter
    private String method;

    public void setService(String service) {
        this.service = service;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getService() {
        return service;
    }

    public String getMethod() {
        return method;
    }

    ForwardEnum(String service, String method, String code) {
        this.service = service;
        this.method = method;
        this.code = code;
    }

    public static ForwardEnum getByService(String method) {
        for (ForwardEnum forwardEnum : values()) {
            if (forwardEnum.getMethod() == method) {
                return forwardEnum;
            }
        }
        return null;
    }

    public static String getByMethod(String service) {
        if (service == null) {
            return null;
        }
        ForwardEnum[] forwardEnums = values();
        for (ForwardEnum forwardEnum : forwardEnums) {
            if (forwardEnum.getService().equals(service)) {
                return forwardEnum.getMethod();
            }
        }
        return null;
    }

    public static String getServiceByCode(String code) {
        if (code == null) {
            return null;
        }
        ForwardEnum[] forwardEnums = values();
        for (ForwardEnum forwardEnum: forwardEnums) {
            if (forwardEnum.getCode().equals(code)) {
                return forwardEnum.getService();
            }
        }
        return null;
    }

    public static String getMethodByCode(String code) {
        if (code == null) {
            return null;
        }
        ForwardEnum[] forwardEnums = values();
        for (ForwardEnum forwardEnum: forwardEnums) {
            if (forwardEnum.getCode().equals(code)) {
                return forwardEnum.getMethod();
            }
        }
        return null;
    }

    public static Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        for (ForwardEnum forwardEnum: ForwardEnum.values()) {
            map.put(forwardEnum.getCode(), forwardEnum.getService());
            map.put(forwardEnum.getService(), forwardEnum.getMethod());
            map.put(forwardEnum.getMethod(), forwardEnum.getCode());
        }
        return map;
    }


}
