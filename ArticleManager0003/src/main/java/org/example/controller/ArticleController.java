package org.example.controller;

import org.example.util.Util;
import org.example.dto.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArticleController extends Controller {

    private static Scanner sc;
    private static String cmd;
    private static int lastArticleId = 3;
    static List<Article> articles;

    public ArticleController(Scanner sc){
        this.sc = sc;
        articles = new ArrayList<>();
    }

    public void doAction(String cmd, String actionMethodName){
        this.cmd = cmd;

        switch(actionMethodName){
            case "write":
                doWrite();
                break;
            case "list":
                doList();
                break;
            case "detail":
                doDetail();
                break;
            case "delete":
                doDelete();
                break;
            case "modify":
                doModify();
                break;
            default:
                System.out.println("Invalid action method");
                break;
        }
    }

    private void doWrite(){
        System.out.println("== 게시글 작성 ==");

        int id = ++lastArticleId ;

        System.out.print("제목 : ");
        String title = sc.nextLine().trim();

        System.out.print("내용 : ");
        String body = sc.nextLine().trim();

        String regDate = Util.getNowStr();
        String updateDate = Util.getNowStr();

        Article article = new Article(id, regDate, updateDate, title, body, loginedMember.getId());

        articles.add(article);

        System.out.printf("%d번 글이 작성되었습니다.\n", id);

        lastArticleId++;

    }

    private void doList(){
        System.out.println("== 게시물 목록 ==");
        if (articles.size() == 0) {
            System.out.println("게시글이 존재하지 않습니다.");
            return;
        }
        String searchKeyword = cmd.substring("article list".length()).trim();

        List<Article> forPrintArticles = articles;

        if (searchKeyword.length() > 0) {
            System.out.println("검색어 : " + searchKeyword);
            forPrintArticles = new ArrayList<>();

            for (Article article : articles) {
                if (article.getTitle().contains(searchKeyword)) {
                    forPrintArticles.add(article);
                }
            }
            if (forPrintArticles.size() == 0) {
                System.out.println("검색 결과 없음");
                return;
            }
        }
        System.out.println(" 번호  |  날짜  |  제목  |  내용  ");
        for (int i = articles.size() - 1; i >= 0; i--) {
            Article article = articles.get(i);
            if (Util.getNowStr().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
                System.out.printf(" %d  |  %s  |  %s  |  %s  \n", article.getId(), article.getRegDate().split(" ")[1], article.getTitle(), article.getBody());
            } else {
                System.out.printf(" %d  |  %s  |  %s  |  %s  \n", article.getId(), article.getRegDate().split(" ")[0], article.getTitle(), article.getBody());
            }
        }
    }

    public static void doDelete(){

        System.out.println("== 게시글 삭제 ==");
        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticeById(id);

        if (foundArticle == null) {
            System.out.println("해당 게시글은 없습니다.");
            return;
        }
        if(foundArticle.getMemberId() != loginedMember.getId()){
            System.out.println("권한 없음");
            return;
        }
        articles.remove(foundArticle);

        System.out.println(id + "번 게시글이 삭제되었습니다.");
    }

    private void doDetail(){

        System.out.println("== 게시글 상세보기 == ");
        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticeById(id);

        if (foundArticle == null) {
            System.out.println("해당 게시글은 없습니다.");
        }
        else {
            System.out.println("번호 : " + foundArticle.getId());
            System.out.println("작성날짜 : " + foundArticle.getRegDate());
            System.out.println("수정날짜 : " + foundArticle.getUpdateDate());
            System.out.println("작성자 : " + foundArticle.getMemberId());
            System.out.println("제목 : " + foundArticle.getTitle());
            System.out.println("내용 : " + foundArticle.getBody());
        }
    }

    private void doModify(){
        System.out.println("== 게시글 수정 ==");
        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticeById(id);

        if (foundArticle == null) {
            System.out.println("해당 게시글은 없습니다.");
            return;
        }

        if(foundArticle.getMemberId() != loginedMember.getId()){
            System.out.println("권한 없음");
            return;
        }

        System.out.println("기존 title : " + foundArticle.getTitle());
        System.out.println("기존 body : " + foundArticle.getBody());

        System.out.print("새 제목 : ");
        String newTitle = sc.nextLine().trim();

        System.out.print("새 내용 : ");
        String newBody = sc.nextLine().trim();

        foundArticle.setTitle(newTitle);
        foundArticle.setBody(newBody);

        foundArticle.setUpdateDate(Util.getNowStr());
        System.out.println(id + "번 게시글이 수정되었습니다.");

    }

    static Article getArticeById(int id) {
        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }

    // 게시글 테스트 데이터 생성
    public static void makeTestData() {
        System.out.println("== 테스트 데이터 생성 ==");
        articles.add(new Article(1, "2026-04-08 12:22:02", "2026-04-09 12:22:02", "제목1", "내용1",1));
        articles.add(new Article(2, Util.getNowStr(), Util.getNowStr(), "제목2", "내용2",1));
        articles.add(new Article(3, Util.getNowStr(), Util.getNowStr(), "제목3", "내용3",2));
    }

}
