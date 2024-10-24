package com.taskapp.logic;

import com.taskapp.dataaccess.UserDataAccess;
import com.taskapp.exception.AppException;
import com.taskapp.model.User;

public class UserLogic {
    private final UserDataAccess userDataAccess;

    public UserLogic() {
        userDataAccess = new UserDataAccess();
    }

    public User findByCode(int code) {
        // UserDataAccessからユーザーを取得
        return userDataAccess.findByCode(code);
    }

    /**
     * 自動採点用に必要なコンストラクタのため、皆さんはこのコンストラクタを利用・削除はしないでください
     * @param userDataAccess
     */
    public UserLogic(UserDataAccess userDataAccess) {
        this.userDataAccess = userDataAccess;
    }

    /**
     * ユーザーのログイン処理を行います。
     *
     * @see com.taskapp.dataaccess.UserDataAccess#findByEmailAndPassword(String, String)
     * @param email ユーザーのメールアドレス
     * @param password ユーザーのパスワード
     * @return ログインしたユーザーの情報
     * @throws AppException メールアドレスとパスワードが一致するユーザーが存在しない場合にスローされます
     */
    //// ユーザーがログインするためのメソッド
    public User login(String email, String password) throws AppException {
    // メールアドレスとパスワードを使用してユーザーをデータベースから検索
    User user = userDataAccess.findByEmailAndPassword(email, password);
    // ユーザーが見つからない場合、例外をスロー
    if (user == null) {
        throw new AppException("既に登録されているメールアドレス、パスワードを入力してください");
    }
    return user;
}

}