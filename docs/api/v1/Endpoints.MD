# Endpoints (v1)

(For testing, ensure that you create the Entities User and Category, available in the second version of the api. If for any reason postman don't automatically recognizes the user and password, after importing the collection, in Authorization select "Inherit auth from parent").

| US | METHOD | Endpoint | Body/Param | Validations |
| :---: | :---: | :---: | :---: | :---: |
| 01 | POST | {{localhost}}/api/v1/user/**{userId}**/follow/**{userIdToFollow}** | userId: User that's following // userIdToFollow: User that's being followed | User Following Exists / User being Followed Exists / Follow a normal User (Not Seller) / Following same id |
| 02 | GET | {{localhost}}/api/v1/user/**{userId}**/followers/count | userId: User that the followers count will be obtained | User Exists / Normal User (Not Seller) |
| 03 | GET | {{localhost}}/api/v1/user/**{userId}**/followers/list | userId: User that the followers list will be obtained | User Exists / Normal User (Not Seller) |
| 04 | GET | {{localhost}}/api/v1/user/**{userId}**/followed/list | userId: User that the followed list will be obtained | User Exists |
| 05 | POST | {{localhost}}/api/v1/products/newPost | [New Post Body](NewPost.json)  | User Exists / Category Exists / Normal User (Not Seller) |
| 06 | GET | {{localhost}}/api/v1/products/followed/**{userId}**/list | userId: User that follows sellers from where posts will be obtained | User exists |
| 07 | POST | {{localhost}}/api/v1/user/**{userId}**/unfollow/**{userIdToUnfollow}** | userId: User that's unfollowing // userIdToUnfollow: User that's being unfollowed | User Unfollowing Exists / User being Unfollowed Exists / Unfollow a normal User (Not Seller) / Unfollowing same id |
| 08 | GET | {{localhost}}/api/v1/user/**{userId}**/followers/list?order=**{order}** // {{localhost}}/api/v1/user/**{userId}**/followed/list?order=**{order}** | userId: User from were follower/followed list will be made // order: Order by name, ascending or descending (name_asc, name_desc) | N/A |
| 09 | GET | {{localhost}}/api/v1/user/**{userId}**/followers/list?order=**{order}** // {{localhost}}/api/v1/user/**{userId}**/followed/list?order=**{order}** | userId: User from were follower/followed list will be made // order: Order by date, ascending or descending (date_asc, date_desc) | N/A |
| 10 (Bonus) | POST | {{localhost}}/api/v1/products/newPromoPost | [New Promo Post Body](NewPromoPost.json) | User Exists / Category Exists / Normal User (Not Seller) |
| 11 (Bonus) | GET | {{localhost}}/api/v1/products/**{userId}**/countPromo | userId: User that promo post count will be obtained | User Exists / Normal User (Not Seller) |
| 12 (Bonus) | GET | {{localhost}}/api/v1/products/**{userId}**/list | userId: User that promo post list will be obtained | User Exists / Normal User (Not Seller) |