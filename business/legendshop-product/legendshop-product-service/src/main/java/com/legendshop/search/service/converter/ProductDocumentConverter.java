/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.search.service.converter;

import com.legendshop.common.core.service.BaseConverter;
import com.legendshop.search.document.ProductDocument;
import com.legendshop.search.dto.ProductDocumentDTO;
import org.mapstruct.Mapper;

/**
 * @author legendshop
 */
@Mapper
public interface ProductDocumentConverter extends BaseConverter<ProductDocument, ProductDocumentDTO> {

}
