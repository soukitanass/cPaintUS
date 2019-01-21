package cPaintUS.models.saveStrategy;

import java.io.File;

import cPaintUS.models.saveStrategy.FileContext.types;
import javafx.scene.layout.Pane;

public interface FileManagerStrategy {

	public void save(Pane pane, File file);

	public void load(String path);
	
}
