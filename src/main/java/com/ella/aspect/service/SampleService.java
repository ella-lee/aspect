package com.ella.aspect.service;

import java.util.Optional;

import com.ella.aspect.model.SampleObject;

public interface SampleService {

	public Optional<SampleObject> get(Long id);
	public SampleObject update(Long id, SampleObject sampleObject);
	
}
