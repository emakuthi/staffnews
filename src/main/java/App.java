import static spark.Spark.*;

import com.google.gson.Gson;
import exceptions.ApiException;
import models.Article;
import models.Department;
import models.User;
import models.dao.DB;
import models.dao.Sql2OUserDao;
import models.dao.Sql2ODepartmentDao;
import models.dao.Sql2OArticleDao;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {

        Sql2OUserDao userDao;
        Sql2ODepartmentDao departmentDao;
        Sql2OArticleDao articleDao;
        Connection conn;
        Gson gson = new Gson();

        staticFileLocation("/public");
        departmentDao = new Sql2ODepartmentDao(DB.sql2o);
        userDao = new Sql2OUserDao(DB.sql2o);
        articleDao = new Sql2OArticleDao(DB.sql2o);
        conn = DB.sql2o.open();


        ProcessBuilder process=new ProcessBuilder();
        Integer port;

        if(process.environment().get("PORT")!=null){
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        port(port);

        //CREATE
        post("/departments/:departmentId/user/:userId", "application/json", (req, res) -> {

            int departmentId = Integer.parseInt(req.params("departmentId"));
            int userId = Integer.parseInt(req.params("userId"));
            Department department = departmentDao.findById(departmentId);
            User user = userDao.findById(userId);


            if (department != null && user != null){
                //both exist and can be associated
                userDao.addUserToDepartment(user, department);
                res.status(201);
                return gson.toJson(String.format("Department '%s' and User '%s' have been associated", user.getName(), department.getName()));
            }
            else {
                throw new ApiException(404, String.format("Department or User does not exist"));
            }
        });

        get("/departments/:id/users", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            else if (departmentDao.getAllUsersByDepartment(departmentId).size()==0){
                return "{\"message\":\"I'm sorry, but no users are listed for this department.\"}";
            }
            else {
                return gson.toJson(departmentDao.getAllUsersByDepartment(departmentId));
            }
        });

        get("/users/:id/departments", "application/json", (req, res) -> {
            int userId = Integer.parseInt(req.params("id"));
            User userToFind = userDao.findById(userId);
            if (userToFind == null){
                throw new ApiException(404, String.format("No user with the id: \"%s\" exists", req.params("id")));
            }
            else if (userDao.getAllDepartmentsForAUser(userId).size()==0){
                return "{\"message\":\"I'm sorry, but no departments are listed for this user.\"}";
            }
            else {
                return gson.toJson(userDao.getAllDepartmentsForAUser(userId));
            }
        });


        post("/departments/:departmentId/articles/new", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("departmentId"));
            Article article = gson.fromJson(req.body(), Article.class);

            article.setDepartmentId(departmentId);
            articleDao.add(article);
            res.status(201);
            return gson.toJson(article);
        });

        post("/users/new", "application/json", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            userDao.add(user);
            res.status(201);
            return gson.toJson(user);
        });

        //READ
        get("/departments", "application/json", (req, res) -> {
            System.out.println(departmentDao.getAll());

            if(departmentDao.getAll().size() > 0){
                return gson.toJson(departmentDao.getAll());
            }

            else {
                return "{\"message\":\"I'm sorry, but no departments are currently listed in the database.\"}";
            }

        });

        get("/departments/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(departmentToFind);
        });

        get("/departments/:id/articles", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));

            Department departmentToFind = departmentDao.findById(departmentId);
            List<Article> allArticles;

            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }

            allArticles = articleDao.getAllArticlesByDepartment(departmentId);

            return gson.toJson(allArticles);
        });

        get("/users", "application/json", (req, res) -> {
            return gson.toJson(userDao.getAll());
        });


        //CREATE
        post("/departments/new", "application/json", (req, res) -> {
            Department department = gson.fromJson(req.body(), Department.class);
            departmentDao.add(department);
            res.status(201);
            return gson.toJson(department);
        });

        //FILTERS
        exception(ApiException.class, (exception, req, res) -> {
            ApiException err = exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });


        after((req, res) -> {

            res.type("application/json");
        });

    }
}