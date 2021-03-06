package parsers;

import java.io.IOException;

import components.FilesMap;
import models.simulation.Structure;

public interface IParser {
	public Structure Parse(FilesMap files) throws IOException;
}
