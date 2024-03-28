package com.anderson.ordermanager.app.gateways;

import com.anderson.ordermanager.infra.web.dto.EmailDto;

public interface EmailGateway {
	void send(EmailDto email);
}
