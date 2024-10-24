package com.taskapp.dataaccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.taskapp.model.User;

public class UserDataAccess {
    private final String filePath;

    public UserDataAccess() {
        filePath = "app/src/main/resources/users.csv";
    }

    /**
     * 自動採点用に必要なコンストラクタのため、皆さんはこのコンストラクタを利用・削除はしないでください
     * @param filePath
     */
    public UserDataAccess(String filePath) {
        this.filePath = filePath;
    }

    /**
     * メールアドレスとパスワードを基にユーザーデータを探します。
     * @param email メールアドレス
     * @param password パスワード
     * @return 見つかったユーザー
     */
    // メールアドレスとパスワードでユーザーを検索するメソッド
    public User findByEmailAndPassword(String email, String password) {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = br.readLine()) != null) { // ファイルの各行を読み込む
            String[] data = line.split(","); // 行をカンマで分割して配列に格納
            if (data.length == 4) { // ファイルから読み取ったメールアドレスとパスワードを取得
                String fileEmail = data[2].trim();
                String filePassword = data[3].trim();
                if (fileEmail.equals(email) && filePassword.equals(password)) {
                    return new User(Integer.parseInt(data[0]), data[1], fileEmail, filePassword);
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return null;
}


    /**
     * コードを基にユーザーデータを取得します。
     * @param code 取得するユーザーのコード
     * @return 見つかったユーザー
     */
    public User findByCode(int code) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // ヘッダー行をスキップ
            
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    try {
                        int userCode = Integer.parseInt(data[0].trim());
                        if (userCode == code) {
                            return new User(userCode, data[1], data[2], data[3]);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("数値変換エラー: " + data[0]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    
    
}
