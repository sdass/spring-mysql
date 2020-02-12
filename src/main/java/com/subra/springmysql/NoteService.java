package com.subra.springmysql;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.subra.springmysql.model.Notes;
import com.subra.springmysql.model.NotesRepository;


@Service
public class NoteService {

	@Autowired
	NotesRepository repo;
	
	List<Notes> getAllNotes(){
		return repo.findAll();
	}
	
	@Transactional(propagation  = Propagation.REQUIRED)
	Notes createANote(Notes note){		
		return repo.save(note);
	}
	

	@Transactional(readOnly = true)
	Optional<Notes> getOneNote2(Long id){
		Optional<Notes> retNote; 
		try {
			retNote =  Optional.of(repo.getOne(id));
		}catch(Exception e) {
			retNote = Optional.empty();
		}
		return retNote;
		
	}


	@Transactional(readOnly = true)
	Optional<Notes> getOneNote(Long id){
		return repo.findById(id);
		
	}
	
}
