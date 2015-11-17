package com.sany;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by Abu on 22.09.2015.
 */
public class ToDoMVCTest {

    ElementsCollection tasks =  $$("#todo-list>li");

    @BeforeClass
    public static void openToDoMvs(){

        open("http://todomvc.com/examples/troopjs_require/#");

    }
    @Before
    public void clearData(){
        open("http://todomvc.com/");
        open("http://todomvc.com/examples/troopjs_require/#");

    }
    @After
    public void clearData2(){
        executeJavaScript("localStorage.clear()");


    }


    @Test
    public  void testAtAllFilter(){



        //todo create task
        addTask("1");
        addTask("2");
        addTask("3");
        addTask("4");
        tasks.shouldHave(exactTexts("1", "2", "3", "4"));

        //todo delete task
        tasks.find(text("2")).hover().find(".destroy").click();

        //todo: change to : delete "2"
        tasks.shouldHave(texts("1", "3", "4"));

        // todo: mark task completed and clear
        tasks.find(text("4")).find(".toggle").click();
        clearCompleted();
        tasks.shouldHave(texts("1", "3"));

         // todo: mark all task at completed and clear
        $("#toggle-all").click();
        clearCompleted();
        tasks.shouldBe(empty);






    }
    @Test
    public void testAtActiveFilter(){

        //todo: Given
        addTask("a");
        addTask("b");
        addTask("c");
        toggle("c");

        // todo: filter active tasks at active filter
        $(By.linkText("Active")).click();
        tasks.filter(visible).shouldHave(texts("a", "b"));

        //todo: ad task
        addTask("d from active");
        tasks.filter(visible).shouldHave(texts("a", "b", "d from active"));
        $(By.linkText("All")).click();
        tasks.filter(visible).shouldHave(texts("a", "b", "c", "d from active"));
        $(By.linkText("Active")).click();



    }
    @Test
    public void testAtCompletedFilter(){

        //todo: Given
        addTask("a");
        addTask("b");
        addTask("c");
        toggle("b");
        toggle("c");

        //todo: filter completed task at completed filter
        $(By.linkText("Completed")).click();
        tasks.filter(visible).shouldHave(texts("b","c"));

        //todo: reopen
        toggle("b");
        tasks.filter(visible).shouldHave(texts("c"));



    }
    public  void addTask(String text){
        $("#new-todo").setValue(text).pressEnter();

    }
    public  void clearCompleted(){
        $("#clear-completed").click();
    }
    public void toggle(String taskText){
        tasks.find(text(taskText)).find(".toggle").click();
    }
}
