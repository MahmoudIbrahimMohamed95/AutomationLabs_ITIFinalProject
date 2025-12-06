package businessPages;
import org.openqa.selenium.WebDriver;
import readers.JsonReader;

public enum QA_Categories {

    MEN_WEAR("") {
            @Override
            public IQA_ProductPage createPage(WebDriver driver) {
                return new QA_MenFashion(driver);
            }
           @Override
            public String getKey() {
                return QA_Categories.getKey("Men_Fashion");
            }
    },

    WOMEN_WEAR("") {
            @Override
            public IQA_ProductPage createPage(WebDriver driver) {
                return new QA_WomenFashion(driver);
            }
            @Override
            public String getKey() {
                return QA_Categories.getKey("Women_Fashion");
            }
        },

    KIDS_WEAR("") {
            @Override
            public IQA_ProductPage createPage(WebDriver driver) {
                return new QA_KidsFashion(driver);
            }
            @Override
            public String getKey() {
                return super.getKey("Kids_Fashion");
            }
        },

    ELECTRONICS("") {
            @Override
            public IQA_ProductPage createPage(WebDriver driver) {
                return new QA_Electronics(driver);
            }
            @Override
            public String getKey() {
                return super.getKey("Electronics");
            }
        };

        private static final JsonReader reader = new JsonReader("ShopCategories");
        private  String key;


        QA_Categories(String key) {
            this.key = key;
        }

        public static QA_Categories fromKey(String key) {
            for (QA_Categories type : values()) {
                if (type.getKey().equalsIgnoreCase(key)) {
                    return type;
                }
            }
            throw new UnsupportedOperationException("Unknown category key: " + key);
        }

        public static String getKey(String jsonKey)
        {
            String value = reader.getJsonData(jsonKey);
            System.out.println("[DEBUG] " + jsonKey + " => " + value);
            return value;
        }

        public  String getKey()
        {
            return key;
        }

        public abstract IQA_ProductPage createPage(WebDriver driver);

}
