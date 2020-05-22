package com.ella.aspect.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ella.aspect.model.SampleObject;

@Service
public class SampleServiceImpl implements SampleService {

	@Override
	public Optional<SampleObject> get(Long id) {
		SampleObject object = new SampleObject();
		object.setId(id);
		object.setOwner("ella");
		object.setTitle("title");
		object.setDescription("description");
		// get logic
		
		Optional<SampleObject> result = Optional.of(object);
		return result;
	}

	@Override
	public SampleObject update(Long id, SampleObject sampleObject) {
		SampleObject result = new SampleObject();
		result.setId(id);
		result.setTitle("new title");
		result.setDescription("new description");
		// update logic
		
		return result;
	}

}
