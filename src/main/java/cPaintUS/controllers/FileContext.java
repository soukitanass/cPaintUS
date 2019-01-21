package cPaintUS.controllers;

import cPaintUS.models.saveStrategy.PNGStrategy;
import cPaintUS.models.saveStrategy.XMLStrategy;

public class FileContext {
	
	public enum types {
		PNG, XML
	}
	
	public FileContext() {

	}
		
	public void save (types type) {
		switch(type) {
			case PNG : 
				PNGStrategy pngstrategy = new PNGStrategy ();
				break; 
			case XML : 
				XMLStrategy xmlstrategy = new XMLStrategy ();
				break; 
			default : 
				System.err.println ("Type d'enregistrement non reconnu"); 
				break; 
		}
	}
}
