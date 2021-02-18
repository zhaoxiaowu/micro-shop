package com.zxw.microshop.storage.model;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


@Data
@TableName("tb_storage")
public class Storage implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String produceCode;
	private Long count;

}
