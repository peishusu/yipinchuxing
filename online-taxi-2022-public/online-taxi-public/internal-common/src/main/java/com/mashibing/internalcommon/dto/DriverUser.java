package com.mashibing.internalcommon.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @program: online-taxi-public
 * @description:
 * @author: lydms
 * @create: 2024-03-19 11:22
 **/
@Data
public class DriverUser {
    /**
     *
     */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Long id;
    /**
     *
     */
    @Size(max= 6,message="编码长度不能超过6")
    @ApiModelProperty("")
    @Length(max= 6,message="编码长度不能超过6")
    private String address;
    /**
     *
     */
    @Size(max= 16,message="编码长度不能超过16")
    @ApiModelProperty("")
    @Length(max= 16,message="编码长度不能超过16")
    private String driverName;
    /**
     *
     */
    @Size(max= 16,message="编码长度不能超过16")
    @ApiModelProperty("")
    @Length(max= 16,message="编码长度不能超过16")
    private String driverPhone;
    /**
     *
     */
    @ApiModelProperty("")
    private Integer driverGender;
    /**
     *
     */
    @ApiModelProperty("")
    private LocalDate driverBirthday;
    /**
     * 民族
     */
    @ApiModelProperty("民族")
    private String driverNation;
    /**
     * 驾驶员联系地址
     */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("驾驶员联系地址")
    @Length(max= 255,message="编码长度不能超过255")
    private String driverContactAddress;
    /**
     * 机动车驾驶证号
     */
    @ApiModelProperty("机动车驾驶证号")
    private String licenseId;
    /**
     * 初次领取驾驶证日期
     */
    @ApiModelProperty("初次领取驾驶证日期")
    private LocalDate getDriverLicenseData;
    /**
     * 驾驶证有效期起
     */
    @ApiModelProperty("驾驶证有效期起")
    private LocalDate driverLicenseOn;
    /**
     * 驾驶证有效期截止
     */
    @ApiModelProperty("驾驶证有效期截止")
    private LocalDate driverLicenseOff;
    /**
     * 是否迅游出租车 1：是 2：否
     */
    @ApiModelProperty("是否迅游出租车 1：是 2：否")
    private Integer taxiDriver;
    /**
     * 驾驶员资格证号
     */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("驾驶员资格证号")
    @Length(max= 255,message="编码长度不能超过255")
    private String certificateNo;
    /**
     * 驾驶员资格证发证日期
     */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("驾驶员资格证发证日期")
    @Length(max= 255,message="编码长度不能超过255")
    private String networkCarIssueOrganization;
    /**
     * 资格证发证日期
     */
    @ApiModelProperty("资格证发证日期")
    private LocalDate networkCarIssueDate;
    /**
     * 初次领取资格证日期
     */
    @ApiModelProperty("初次领取资格证日期")
    private LocalDate getNetworkCarProofDate;
    /**
     * 资格证有效起始日期
     */
    @ApiModelProperty("资格证有效起始日期")
    private LocalDate networkCarProofOn;
    /**
     * 资格证有效截止日期
     */
    @ApiModelProperty("资格证有效截止日期")
    private LocalDate networkCarProofOff;
    /**
     * 报备日期
     */
    @ApiModelProperty("报备日期")
    private LocalDate registerDate;
    /**
     * 1:网络预约出租汽车 2：巡游出租汽车 3：私人小客车
     */
    @ApiModelProperty("1:网络预约出租汽车 2：巡游出租汽车 3：私人小客车")
    private Integer commercialType;
    /**
     * 驾驶员签约合同全称
     */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("驾驶员签约合同全称")
    @Length(max= 255,message="编码长度不能超过255")
    private String contractCompany;
    /**
     * 合同有效期起
     */
    @ApiModelProperty("合同有效期起")
    private LocalDate contractOn;
    /**
     * 合同有效期止
     */
    @ApiModelProperty("合同有效期止")
    private LocalDate contractOff;
    /**
     * 0:有效 1：无效
     */
    @ApiModelProperty("0:有效 1：无效")
    private Integer state;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime gmtCreate;
    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private LocalDateTime gmtModified;
}
