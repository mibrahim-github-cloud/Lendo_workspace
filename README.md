## Lendo_workspace

This is about to describe the features used in this assessment. I have used H2-In Memory DB for data persistence. For Authentication layer used JWT implementation along with Spring Security. Below is the list of APIs Supported.


Note: Except "Create User" and "User Login" API all other APIs required JWT Authorization Token to get served. Else we will get "Forbidden" response error code.

### User APIs
  * Create User(http://localhost:8081/users)
  * User Login(http://localhost:8081/users/login)
  * Get All Users(http://localhost:8081/users)
  * Get User By ID (http://localhost:8081/users/{userId})
  
### Post APIs
 * Create Post(http://localhost:8081/posts)
 * GetAllPosts(http://localhost:8081/posts)
 * GetUserPosts(http://localhost:8081/posts/{userId}
 
### Comments APIs
 * Create Comment(http://localhost:8081/comments)
 * GetAllComments(http://localhost:8081/comments)
 * GetCommentsByPost(http://localhost:8081/comments/{postId}
