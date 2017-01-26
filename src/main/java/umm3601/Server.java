package umm3601;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import umm3601.user.UserController;

import java.io.IOException;

import static spark.Spark.*;

public class Server {
    public static void main(String[] args) throws IOException {
        staticFiles.location("/public");
        Gson gson = new Gson();
        UserController userController = new UserController();

        // Simple example route
        get("/hello", (req, res) -> "Hello World");

        // Redirects for the "home" page
        redirect.get("", "/");
        redirect.get("/", "/index.html");

        // Redirect for the "about" page
        redirect.get("/about", "/about.html");

        // Redirect for the Pet Form
        redirect.get("/petForm", "/petForm.html");

        // List users
        get("/users", (req, res) -> {
            res.type("application/json");
            return wrapInJson("users", gson.toJsonTree(userController.listUsers()));
        });

        // Filter users by specific age
        get("/users/age/:age", (req, res) -> {
            res.type("application/json");
            int age = Integer.parseInt(req.params("age"));
            return wrapInJson("users", gson.toJsonTree(userController.listUsersByAge(age)));
        });
    }

    public static JsonObject wrapInJson(String name, JsonElement jsonElement) {
        JsonObject result = new JsonObject();
        result.add(name, jsonElement);
        return result;
    }

}
