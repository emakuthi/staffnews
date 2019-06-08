import static spark.Spark.*;

import com.google.gson.Gson;
import exceptions.ApiException;
import models.Article;
import models.Department;
import models.Staff;
import models.dao.Sql2OStaffDao;
import models.dao.Sql2ODepartmentDao;
import models.dao.Sql2oReviewDao;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        Sql2OStaffDao foodtypeDao;
        Sql2ODepartmentDao restaurantDao;
        Sql2oReviewDao reviewDao;
        Connection conn;
        Gson gson = new Gson();

        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");

        restaurantDao = new Sql2ODepartmentDao(sql2o);
        foodtypeDao = new Sql2OStaffDao(sql2o);
        reviewDao = new Sql2oReviewDao(sql2o);
        conn = sql2o.open();

        //CREATE
        post("/restaurants/:restaurantId/foodtype/:foodtypeId", "application/json", (req, res) -> {

            int restaurantId = Integer.parseInt(req.params("restaurantId"));
            int foodtypeId = Integer.parseInt(req.params("foodtypeId"));
            Department department = restaurantDao.findById(restaurantId);
            Staff staff = foodtypeDao.findById(foodtypeId);


            if (department != null && staff != null){
                //both exist and can be associated
                foodtypeDao.addFoodtypeToRestaurant(staff, department);
                res.status(201);
                return gson.toJson(String.format("Department '%s' and Staff '%s' have been associated", staff.getName(), department.getName()));
            }
            else {
                throw new ApiException(404, String.format("Department or Staff does not exist"));
            }
        });

        get("/restaurants/:id/foodtypes", "application/json", (req, res) -> {
            int restaurantId = Integer.parseInt(req.params("id"));
            Department departmentToFind = restaurantDao.findById(restaurantId);
            if (departmentToFind == null){
                throw new ApiException(404, String.format("No restaurant with the id: \"%s\" exists", req.params("id")));
            }
            else if (restaurantDao.getAllFoodtypesByRestaurant(restaurantId).size()==0){
                return "{\"message\":\"I'm sorry, but no foodtypes are listed for this restaurant.\"}";
            }
            else {
                return gson.toJson(restaurantDao.getAllFoodtypesByRestaurant(restaurantId));
            }
        });

        get("/foodtypes/:id/restaurants", "application/json", (req, res) -> {
            int foodtypeId = Integer.parseInt(req.params("id"));
            Staff staffToFind = foodtypeDao.findById(foodtypeId);
            if (staffToFind == null){
                throw new ApiException(404, String.format("No foodtype with the id: \"%s\" exists", req.params("id")));
            }
            else if (foodtypeDao.getAllRestaurantsForAFoodtype(foodtypeId).size()==0){
                return "{\"message\":\"I'm sorry, but no restaurants are listed for this foodtype.\"}";
            }
            else {
                return gson.toJson(foodtypeDao.getAllRestaurantsForAFoodtype(foodtypeId));
            }
        });


        post("/restaurants/:restaurantId/reviews/new", "application/json", (req, res) -> {
            int restaurantId = Integer.parseInt(req.params("restaurantId"));
            Article article = gson.fromJson(req.body(), Article.class);

            article.setRestaurantId(restaurantId); //we need to set this separately because it comes from our route, not our JSON input.
            reviewDao.add(article);
            res.status(201);
            return gson.toJson(article);
        });

        post("/foodtypes/new", "application/json", (req, res) -> {
            Staff staff = gson.fromJson(req.body(), Staff.class);
            foodtypeDao.add(staff);
            res.status(201);
            return gson.toJson(staff);
        });

        //READ
        get("/restaurants", "application/json", (req, res) -> {
            System.out.println(restaurantDao.getAll());

            if(restaurantDao.getAll().size() > 0){
                return gson.toJson(restaurantDao.getAll());
            }

            else {
                return "{\"message\":\"I'm sorry, but no restaurants are currently listed in the database.\"}";
            }

        });

        get("/restaurants/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int restaurantId = Integer.parseInt(req.params("id"));
            Department departmentToFind = restaurantDao.findById(restaurantId);
            if (departmentToFind == null){
                throw new ApiException(404, String.format("No restaurant with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(departmentToFind);
        });

        get("/restaurants/:id/reviews", "application/json", (req, res) -> {
            int restaurantId = Integer.parseInt(req.params("id"));

            Department departmentToFind = restaurantDao.findById(restaurantId);
            List<Article> allArticles;

            if (departmentToFind == null){
                throw new ApiException(404, String.format("No restaurant with the id: \"%s\" exists", req.params("id")));
            }

            allArticles = reviewDao.getAllReviewsByRestaurant(restaurantId);

            return gson.toJson(allArticles);
        });

        get("/foodtypes", "application/json", (req, res) -> {
            return gson.toJson(foodtypeDao.getAll());
        });


        //CREATE
        post("/restaurants/new", "application/json", (req, res) -> {
            Department department = gson.fromJson(req.body(), Department.class);
            restaurantDao.add(department);
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