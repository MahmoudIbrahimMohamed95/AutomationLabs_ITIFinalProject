package reports;

import fileUtils.FileAndTerminalManager;
import readers.Log;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class AllureSetupEnvironment {

    public static AllureSetupEnvironment setAllureEnvironment() {
        Map<String, String> env = new HashMap<>();
        env.put("OS", System.getProperty("os.name"));
        env.put("Java version:", System.getProperty("java.runtime.version"));
        env.put("Browser", System.getProperty("browserType"));
        env.put("Execution Type", System.getProperty("executionType"));
        env.put("URL", System.getProperty("baseUrlWeb"));


        File envFile=new File(System.getProperty("user.dir")+"/test-output"+"/allure-results/environment.properties");
        envFile.getParentFile().mkdirs();
        try(FileWriter writer=new FileWriter(envFile)){
            for(Map.Entry<String , String>entry:env.entrySet()){
                writer.write(entry.getKey()+"="+entry.getValue()+"\n");
            }
            Log.info("Environment properties file created: "+envFile);
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
        return new AllureSetupEnvironment();
    }

    private static final String BASE_URL =
            "https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/";

    private static final Path BASE_DIR =
            Paths.get(System.getProperty("user.home"), ".m2", "repository", "allure");

    public static final String VERSION = resolveLatestVersion();

    // ===========================================================
    // 1. Resolve latest Allure version using Java HTTP client
    // ===========================================================
    private static String resolveLatestVersion() {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.ALWAYS)
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://github.com/allure-framework/allure2/releases/latest"))
                    .header("User-Agent", "Java HttpClient") // important for GitHub
                    .GET()
                    .build();

            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

            String finalURL = response.uri().toString();
            return finalURL.substring(finalURL.lastIndexOf("/tag/") + 5);

        } catch (Exception e) {
            throw new IllegalStateException("Unable to resolve latest Allure version", e);
        }
    }

    // ===========================================================
    // 2. Public method to download and extract Allure
    // ===========================================================
    public static void downloadAndExtract() {
        try {
            Path versionDir = BASE_DIR.resolve("allure-" + VERSION);

            if (Files.exists(versionDir)) {
                Log.info("Allure already installed: " + versionDir);
                return;
            }

            Files.createDirectories(BASE_DIR);

            Path zipFile = downloadZip();
            extractZip(zipFile);

            if (!isWindows()) {
                setExecutablePermission(getExecutable());
            }

            Log.info("Allure setup finished: " + versionDir);

        } catch (Exception e) {
            Log.error("Error setting up Allure binaries: ", e.getMessage());
        }
    }

    // ===========================================================
    // 3. Download Allure ZIP
    // ===========================================================
    private static Path downloadZip() throws Exception {
        String zipURL = BASE_URL + VERSION + "/allure-commandline-" + VERSION + ".zip";
        Path localZip = BASE_DIR.resolve("allure-" + VERSION + ".zip");

        if (Files.exists(localZip)) {
            Log.info("ZIP already exists: " + localZip);
            return localZip;
        }

        Log.info("Downloading Allure from: " + zipURL);

        try (BufferedInputStream in = new BufferedInputStream(new URI(zipURL).toURL().openStream());
             OutputStream out = Files.newOutputStream(localZip)) {

            in.transferTo(out);
        }

        return localZip;
    }

    // ===========================================================
    // 4. Extract ZIP
    // ===========================================================
    private static void extractZip(Path zipPath) throws Exception {
        try (ZipInputStream zipIn = new ZipInputStream(Files.newInputStream(zipPath))) {

            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {

                Path outputPath = BASE_DIR.resolve(entry.getName());

                if (entry.isDirectory()) {
                    Files.createDirectories(outputPath);
                } else {
                    Files.createDirectories(outputPath.getParent());
                    Files.copy(zipIn, outputPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }

    // ===========================================================
    // 5. Get Allure executable path
    // ===========================================================
    public static Path getExecutable() {
        Path exe = BASE_DIR
                .resolve("allure-" + VERSION)
                .resolve("bin")
                .resolve("allure");

        return isWindows()
                ? exe.resolveSibling("allure.bat")
                : exe;
    }

    // ===========================================================
    // 6. Utilities
    // ===========================================================
    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    private static void setExecutablePermission(Path file) {
        FileAndTerminalManager.executeTerminalCommand("chmod", "u+x", file.toString());
    }
}
