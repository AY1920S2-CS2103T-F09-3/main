package igrad.csvwriter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import igrad.commons.util.FileUtil;
import igrad.model.module.Module;

/**
 * Writes the data stored as a human-readable CSV file.
 */
public class CsvWriter {

    private static final Path filePath = Path.of("study_plan.csv");
    private List<Module> sortedList;

    public CsvWriter(List<Module> sortedList){
        this.sortedList = sortedList;
    }

    /**
     * Writes to CSV
     */
    public void write() throws IOException {
        String data = getHeaders() + getBody();
        FileUtil.writeToFile(filePath, data);
    }

    /**
     * Writes each module as a line. Separates modules taken in different semesters
     * by a new line.
     */
    private String getBody() {

        StringBuilder body = new StringBuilder();

        for (int i = 0; i < sortedList.size(); i++) {
            Module module = sortedList.get(i);

            body.append(module.getSemester().toString());
            body.append(module.getModuleCode().toString());
            body.append(module.getTitle().toString());
            body.append(module.getCredits().toString());

            if (i < sortedList.size() - 1) {
                body.append("\n");
                Module nextModule = sortedList.get(i + 1);
                if (!nextModule.getSemester().equals(module.getSemester())) {
                    body.append("\n");
                }
            }
        }

        return body.toString();
    }

    /**
     * Writes the headers of the CSV file.
     */
    private String getHeaders() {

        StringBuilder headers = new StringBuilder();

        String[] headerArray = {
            "Semester",
            "Module Code",
            "Module Title",
            "MCs"
        };

        for (String header : headerArray) {
            headers.append(header);
            headers.append(",");
        }

        headers.append("\n");

        return headers.toString();
    }

}
