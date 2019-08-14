//package com.xmed.Authentication;
//
//
//
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//
////import com.amazonaws.resources.cognitoidentity.AmazonCognitoIdentityClient;
//
//public class AuthenticateUser implements RequestHandler<Object, Object> {
//
//    private static final String IDENTITY_POOL_ID = "";
//    private static final Regions REGION = Regions.US_WEST_2;
//    private static final String developerProvider = "login.company.developer";
//
//    @Override
//    public AuthenticateUserResponse handleRequest(Object input, Context context) {
//        AuthenticateUserResponse authenticateUserResponse = new AuthenticateUserResponse();
//        @SuppressWarnings("unchecked")
//        LinkedHashMap<String, String> inputHashMap = (LinkedHashMap<String, String>)input;
//        User user = authenticateUser(inputHashMap);
//
//        if(user!=null){
//            authenticateUserResponse.setUserId(user.getUserId());
//            authenticateUserResponse.setStatus("true");
//            authenticateUserResponse.setOpenIdToken(user.getOpenIdToken());
//            authenticateUserResponse.setIdentityId(user.getIdentityId());
//        }else{
//            authenticateUserResponse.setUserId(null);
//            authenticateUserResponse.setStatus("false");
//            authenticateUserResponse.setOpenIdToken(null);
//            authenticateUserResponse.setIdentityId(null);
//        }
//
//        return authenticateUserResponse;
//    }
//
//    public User authenticateUser(LinkedHashMap<String, String> input){
//        User user=null;
//
//        String userName = (String) input.get("userName");
//        String passwordHash = (String) input.get("passwordHash");
//
//        try{
//            //            AmazonDynamoDBClient client = new AmazonDynamoDBClient();
//            //            client.setRegion(Region.getRegion(REGION));
//            final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard()
//                    .withRegion(REGION)
//                    .build();
//            DynamoDBMapper mapper = new DynamoDBMapper(ddb);
//
//            user = mapper.load(User.class, userName);
//
//            if(user!=null){
//                if(user.getPasswordHash().equalsIgnoreCase(passwordHash)){
//                    GetOpenIdTokenForDeveloperIdentityResult result = getOpenIdToken(user.getUserId());
//                    user.setOpenIdToken(result.getToken());
//                    user.setIdentityId(result.getIdentityId());
//                    return user;
//                }
//            }
//        }catch(Exception e){
//            System.out.println(e.toString());
//        }
//        return user;
//    }
//
//    /**
//     * 使用用户ID 调取 CognitoIdentity的 token
//     * @param userId
//     * @return token
//     */
//    private GetOpenIdTokenForDeveloperIdentityResult getOpenIdToken(Integer userId){
//        AmazonCognitoIdentity client = AmazonCognitoIdentityClientBuilder.defaultClient();
//        GetOpenIdTokenForDeveloperIdentityRequest tokenRequest = new GetOpenIdTokenForDeveloperIdentityRequest();
//        tokenRequest.setIdentityPoolId(IDENTITY_POOL_ID);
//
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put(developerProvider, userId.toString());
//
//        tokenRequest.setLogins(map);
//        tokenRequest.setTokenDuration(new Long(10001));
//
//        GetOpenIdTokenForDeveloperIdentityResult result = client.getOpenIdTokenForDeveloperIdentity(tokenRequest);
//        return result;
//        //        String token = result.getToken();
//        //
//        //        return token;
//    }
//}
