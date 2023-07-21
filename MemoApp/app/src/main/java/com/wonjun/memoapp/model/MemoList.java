package com.wonjun.memoapp.model;

import java.util.ArrayList;

public class MemoList {
    private String result;
    private int count;
    private ArrayList<Memo> items;

    // 리스트 안의 JSON을 클래스 작성 방법 1
    // Memo 관련해서 Class를 많이 사용한다. model에 따로 memo를 만들어서 사용하면 편할것 같다.
        // 해당 방법은 2번째 방법
    /*public class Memo{
        private int id;
        private int userId;
        private String title;
        private String content;
        private String memoDate;
        private String createAt;
        private String updateAt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMemoDate() {
            return memoDate;
        }

        public void setMemoDate(String memoDate) {
            this.memoDate = memoDate;
        }

        public String getCreateAt() {
            return createAt;
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
        }

        public String getUpdateAt() {
            return updateAt;
        }

        public void setUpdateAt(String updateAt) {
            this.updateAt = updateAt;
        }
    }*/

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<Memo> getItems() {
        return items;
    }

    public void setItems(ArrayList<Memo> items) {
        this.items = items;
    }
}
