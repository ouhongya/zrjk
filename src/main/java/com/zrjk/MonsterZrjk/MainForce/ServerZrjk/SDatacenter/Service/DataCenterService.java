package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Service;

import com.github.pagehelper.PageInfo;
import com.zrjk.MonsterZrjk.Common.Beans.ParamBean;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Beans.DataArticle;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Beans.DataDoctor;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Beans.DataHealthTheme;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Beans.DataVideo;

public interface DataCenterService {
    PageInfo<DataDoctor> findDoctor(ParamBean paramBean) throws Exception;

    PageInfo<DataArticle> findArticle(ParamBean paramBean) throws Exception;

    PageInfo<DataVideo> findVideo(ParamBean paramBean) throws Exception;

    PageInfo<DataHealthTheme> findHealthTheme(ParamBean paramBean) throws Exception;
}
