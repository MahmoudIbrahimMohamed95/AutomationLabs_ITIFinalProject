package readers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import java.io.File;
import java.util.Iterator;

public class JsonSystemLoader {
    private static final String test_data_path = "src/test/java/testData/";
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Load JSON from any path in the project or file system.
     */
    public static void loadFromFile(String filePath) {
        try {
            JsonNode root = mapper.readTree(new File(test_data_path +filePath+".json"));
            flattenJson(root, "");
        } catch (Exception e) {
            throw new RuntimeException("Failed loading JSON: " + filePath, e);
        }
    }

    /**
     * Recursively flatten nested JSON objects using dot notation.
     * Example: ConfirmationLogin.username = "demo@demo.com"
     * Will NOT overwrite existing System Properties (Maven -D overrides preserved)
     */
    private static void flattenJson(JsonNode node, String prefix) {

        if (node.isObject()) {
            Iterator<String> fieldNames = node.fieldNames();
            while (fieldNames.hasNext()) {
                String field = fieldNames.next();
                flattenJson(node.get(field), prefix + field + ".");
            }
        } else if (node.isValueNode()) {
            String key = prefix.substring(0, prefix.length() - 1); // remove trailing dot
            String value = node.asText();

            // Only set if not already defined (Maven -D or existing system property)
            if (System.getProperty(key) == null) {
                System.setProperty(key, value);
            }
        }
    }
    public static String getDynamicJsonData(String key) {
        try {
            return System.getProperty(key);
        } catch (Exception e) {
            Log.error("Exception in JsonReader getJsonData " ,key, e.getMessage());
            return "";
        }
    }
}
