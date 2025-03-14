/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.common.excel.converters;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author legendshop
 */
public enum LocalDateStringConverter implements Converter<LocalDate> {
	/**
	 * 实例
	 */
	INSTANCE;

	@Override
	public Class supportJavaTypeKey() {
		return LocalDate.class;
	}

	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return CellDataTypeEnum.STRING;
	}

	@Override
	public LocalDate convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
									   GlobalConfiguration globalConfiguration) throws ParseException {
		if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
			return LocalDate.parse(cellData.getStringValue());
		} else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(contentProperty.getDateTimeFormatProperty().getFormat());
			return LocalDate.parse(cellData.getStringValue(), formatter);
		}
	}

	@Override
	public CellData<String> convertToExcelData(LocalDate value, ExcelContentProperty contentProperty,
											   GlobalConfiguration globalConfiguration) {
		DateTimeFormatter formatter;
		if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
			formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		} else {
			formatter = DateTimeFormatter.ofPattern(contentProperty.getDateTimeFormatProperty().getFormat());
		}
		return new CellData<>(value.format(formatter));
	}
}
