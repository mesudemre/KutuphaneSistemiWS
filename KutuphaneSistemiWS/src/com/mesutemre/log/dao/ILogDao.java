package com.mesutemre.log.dao;

import com.mesutemre.log.model.LogModel;
import com.mesutemre.model.ErrorDetail;

public interface ILogDao {

	public ErrorDetail logKaydet(LogModel model);
}
