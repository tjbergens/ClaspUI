package ClaspBackend;

// Shell class for serializing a destroyed account
class DestructionAccount {
    String id;
    String username;
    String email;

    public DestructionAccount(String id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
