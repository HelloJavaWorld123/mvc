package com.jzy.api.service.app.impl;

import com.jzy.api.model.app.AppAccttype;
import com.jzy.api.service.app.AppAccttypeService;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 应用账号service impl
 * @author <a href="mailto:aparatrooper@163.com">aparatrooper</a>
 * @since JDK1.8
 */
@Service
public class AppAccttypeServiceImpl extends GenericServiceImpl<AppAccttype> implements AppAccttypeService {


    @Override
    public List<AppAccttype> list() {
       // String sql = sqlMap("app_accttype.select").concat(sqlMap("app_accttype.order_by.default"));
        return null;
    }

    @Override
    protected GenericMapper<AppAccttype> getGenericMapper() {
        return null;
    }
}
