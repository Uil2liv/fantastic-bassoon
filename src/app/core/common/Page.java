package app.core.common;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.net.URL;

abstract class Page {
    protected URL url;
    protected WebDriver wd;
    protected int scrollOffset;

    public Page(WebDriver wd) {
        this.wd = wd;
        this.scrollOffset = wd.manage().window().getSize().height / 2;
    }

    public Page(String url, WebDriver wd) {
        this.wd = wd;
        this.scrollOffset = wd.manage().window().getSize().height / 2;

        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            System.out.println("Impossible d'accéder à l'adresse : " + url);
            e.printStackTrace();
        }
    }

    public Page(String url) {
        try {
            this.url = new URL(url);
        } catch (java.net.MalformedURLException e) {
            System.out.println("Impossible d'accéder à l'adresse : " + url);
            e.printStackTrace();
        }
    }

    public void access() {
        wd.get(url.toString());
    }

    protected int Scroll(int offset){
        JavascriptExecutor je = (JavascriptExecutor)wd;
        je.executeScript("window.scrollBy(0," + offset + ");", "");

        this.scrollOffset += offset;

        // Check boundaries
        scrollOffset = Math.max(wd.manage().window().getSize().height / 2, scrollOffset);

        return this.scrollOffset;
    }

    protected int ScrollTo(int offset){
        return Scroll(offset - scrollOffset);
    }

    protected int ScrollToTop(){
        return ScrollTo(0);
    }}
