package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    static List<Article> articles;

    public App(){
        articles = new ArrayList<>();
    }

    public void run(){
        Scanner sc = new Scanner(System.in);

        System.out.println("== 프로그램 시작 ==");

        MemberController memberController = new MemberController(sc);

        int lastArticleId = 3;

        makeTestData();
        MemberController.makeTestMember();

        while (true) {
            System.out.print("명령어 > ");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("q")) { // exit
                break;
            } else if (cmd.length() == 0) {
                System.out.println("명령어를 입력해주세요");
                continue;
            }

            if (cmd.equals("mj")) { // member join
                memberController.doJoin();
            }

            else if (cmd.equals("aw")) { // article write
                System.out.println("== 게시글 작성 ==");
                int id = lastArticleId++ ;

                System.out.print("제목 : ");
                String title = sc.nextLine().trim();

                System.out.print("내용 : ");
                String body = sc.nextLine().trim();

                String regDate = Util.getNowStr();
                String updateDate = Util.getNowStr();

                Article article = new Article(id, regDate, updateDate, title, body);
                articles.add(article);

                System.out.printf("%d번 글이 작성되었습니다.\n", id);
                lastArticleId++;

            } else if (cmd.equals("al")) { // article list
                System.out.println("== 게시물 목록 ==");
                if (articles.size() == 0) {
                    System.out.println("게시글이 존재하지 않습니다.");
                    continue;
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
                        continue;
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
            else if (cmd.startsWith("ad")) { // article delete
                System.out.println("== 게시글 삭제 ==");
                int id = Integer.parseInt(cmd.split(" ")[1]);

                Article foundArticle = getArticeById(id);

                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다.");
                    continue;
                }
                articles.remove(foundArticle);
                System.out.println(id + "번 게시글이 삭제되었습니다.");

            } else if (cmd.startsWith("am")) { // article modify
                System.out.println("== 게시글 수정 ==");
                int id = Integer.parseInt(cmd.split(" ")[1]);

                Article foundArticle = getArticeById(id);

                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다.");
                    continue;
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

            } else if (cmd.startsWith("adt")) { // article detail
                System.out.println("== 게시글 상세보기 ==");
                int id = Integer.parseInt(cmd.split(" ")[1]);

                Article foundArticle = getArticeById(id);

                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다.");
                    continue;
                }
                System.out.println("번호 : " + foundArticle.getId());
                System.out.println("작성날짜 : " + foundArticle.getRegDate());
                System.out.println("수정날짜 : " + foundArticle.getUpdateDate());
                System.out.println("제목 : " + foundArticle.getTitle());
                System.out.println("내용 : " + foundArticle.getBody());
            } else {
                System.out.println("사용할 수 없는 명령어 입니다.");
            }
        }
        System.out.println("== 프로그램 종료 ==");
        sc.close();
    }

    private static Article getArticeById(int id) {
        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }

    // 게시글 테스트 데이터 생성
    private static void makeTestData() {
        System.out.println("== 테스트 데이터 생성 ==");
        articles.add(new Article(1, "2026-04-08 12:22:02", "2026-04-09 12:22:02", "제목1", "내용1"));
        articles.add(new Article(2, Util.getNowStr(), Util.getNowStr(), "제목2", "내용2"));
        articles.add(new Article(3, Util.getNowStr(), Util.getNowStr(), "제목3", "내용3"));
    }

}
