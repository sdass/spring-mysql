package com.subra.springmysql;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.subra.springmysql.exception.ResourceNotFoundException;
import com.subra.springmysql.model.Notes;

@RestController
@RequestMapping("/note")
public class NoteController {

	@Autowired 
	NoteService service;
	
	@GetMapping
	public List<Notes> getAllNotes() {
	    return service.getAllNotes();
	}

	@GetMapping("/byId/{idp}")
	public Notes getANote(@Valid @PathVariable("idp") Long id ) {
	   System.out.println("id is" + id);
	   System.out.println("servicecall=" + service.getOneNote(id));
		return service.getOneNote(id).orElseThrow(()-> new ResourceNotFoundException("Notes", "idp", id));
		
		/*
		Optional<Notes> x = service.getOneNote(id);
		 if( x.isPresent()) 
			 return x.get(); 
		 else
			 throw new ResourceNotFoundException("Notes", "idp", id);		
		
		 */
	}
	
	@PostMapping()
	public ResponseEntity<Notes> CreateNote(@Valid @RequestBody Notes note ) {
		Notes retnote = service.createANote(note);
		ResponseEntity<Notes> respent = new ResponseEntity<Notes>(retnote, HttpStatus.ACCEPTED);
		 return respent;
	}
	
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST,value = HttpStatus.BAD_REQUEST, reason = "wrong input")
		void handlerGeneral() {
		
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	//@ResponseStatus(code = HttpStatus.BAD_REQUEST,value = HttpStatus.BAD_REQUEST, reason = "wrong input")
		ResponseEntity<ResourceNotFoundException> handlerResourceNotfound(HttpServletRequest req, ResourceNotFoundException ex) {		
		ResponseEntity<ResourceNotFoundException> respnty = 
				new ResponseEntity<ResourceNotFoundException>(ex, HttpStatus.BAD_REQUEST);
		return respnty;
	}
}
