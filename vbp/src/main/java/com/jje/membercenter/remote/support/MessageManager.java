package com.jje.membercenter.remote.support;

import org.springframework.stereotype.Component;

@Component
public class MessageManager {

//	@Autowired
//	TestHandler testHandler;

	public BaseAnswer receive(BaseReceiver baseReceiver) throws Exception {
		JJETransCode transCode = JJETransCode.valueOf(baseReceiver
				.getTransCode());
		switch (transCode) {
		case J000000:
			return null;//testHandler.test((TestReceiver) baseReceiver);
		default:
			throw new Exception("not fund transCode " + transCode);
		}
	}
}
