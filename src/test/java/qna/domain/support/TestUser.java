package qna.domain.support;

import qna.domain.User;

public class TestUser {

    private TestUser() {

    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {

        private Long id;
        private String userId;
        private String password;
        private String name;
        private String email;

        public UserBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder userId(final String userId) {
            this.userId = userId;
            return this;
        }

        public UserBuilder password(final String password) {
            this.password = password;
            return this;
        }

        public UserBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public UserBuilder email(final String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(id, userId, password, name, email);
        }
    }
}
