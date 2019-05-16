package com.jzy.api.vo.home;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassNameName DealerAppCateVo
 * @Description TODO
 * @Author jiazhiyi
 * @DATE 2019/5/15
 * @Version 1.0
 **/
@Data
public class DealerAppCateVo implements Serializable {
    private Long appCateId;
    private String appCateName;
}
