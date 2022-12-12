package app.com.ChinChen.payload.response;

import java.util.List;

import app.com.ChinChen.domain_temp.Menu1;
import app.com.ChinChen.domain_temp.Menu2;

public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private String id;
  private String username;
  private String email;
  private List<String> roles;
  private List<Menu1> menu1;
  private List<Menu2> menu2;

  public JwtResponse(String accessToken, String id, String username, String email, List<String> roles, List<Menu1> menu1, List<Menu2> menu2) {
    this.token = accessToken;
    this.id = id;
    this.username = username;
    this.email = email;
    this.roles = roles;
    this.menu1 = menu1;
    this.menu2 = menu2;
  }

  public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<String> getRoles() {
    return roles;
  }

  public List<Menu1> getMenu1() {
    return this.menu1;
  }

  public List<Menu2> getMenu2() {
    return this.menu2;
  }
}
