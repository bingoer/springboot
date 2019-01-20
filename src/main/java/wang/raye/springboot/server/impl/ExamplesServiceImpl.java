package wang.raye.springboot.server.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import wang.raye.springboot.server.ExamplesService;

/**
 * Created by wesley on 2017-03-23.
 * exampleServiceImpl
 */
@Service
public class ExamplesServiceImpl implements ExamplesService {

	private static final Logger logger = Logger.getLogger(ExamplesServiceImpl.class);



	@Override
	public void testQuartzJob(String s) {
		System.out.println("Quartz Job service ["+s+"] start!!");
	}

}
