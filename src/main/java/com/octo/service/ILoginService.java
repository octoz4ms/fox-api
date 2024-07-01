package com.octo.service;

import com.octo.dto.request.LoginReq;

import java.util.Map;

public interface ILoginService {

    Map<String, Object> login(LoginReq loginReq);
}
