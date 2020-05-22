package com.ella.aspect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ella.aspect.aspect.AspectAnnotation;
import com.ella.aspect.model.SampleObject;
import com.ella.aspect.service.SampleService;

@RestController
public class SampleController {

	private final SampleService sampleService;
	
	@Autowired
	public SampleController(SampleService sampleService) {
		this.sampleService = sampleService;
	}
	
	@AspectAnnotation(key = "owner")
	@GetMapping(value = "/{id}")
	public SampleObject update(@PathVariable Long id, @RequestBody SampleObject sampleObject) {
		
		// update logic
		SampleObject result = sampleService.update(id, sampleObject);
		
		return result;
	}
}
