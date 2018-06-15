package com.myntra.critico.manager;

import com.myntra.critico.model.Product;
import com.myntra.critico.model.Review;
import org.jinstagram.Instagram;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.entity.users.feed.UserFeed;
import org.jinstagram.entity.users.feed.UserFeedData;
import org.jinstagram.exceptions.InstagramException;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class InstagramManager {

    Twitter twitter;
    RequestToken requestToken;

    public UserInfo processInstaCode(String code) {

        InstagramService service = new InstagramAuthService()
                .apiKey("736dd2369b5d42058a04925bbba80dda")
                .apiSecret("5134d1d6f1b148948d865a31385dae6c")
                .callback("http://localhost:9090/api/instacode")
                .scope("follower_list")
                .build();

        Verifier verifier = new Verifier(code);
        Token accessToken = service.getAccessToken(verifier);
        Instagram instagram = new Instagram(accessToken);
        try {
            UserInfo currentUserInfo = instagram.getCurrentUserInfo();
            UserFeed userFollowedByList = instagram.getUserFollowList(currentUserInfo.getData().getId());

            for (UserFeedData userFeedData : userFollowedByList.getUserList()) {
                MediaFeed recentMediaFeed = instagram.getRecentMediaFeed(userFeedData.getId());
                for (MediaFeedData mediaFeedData : recentMediaFeed.getData()) {
                    for (String tag : mediaFeedData.getTags()) {
                        if (tag.equals("xyz")) {
                            System.out.print("Do something");
                        }
                    }

                }

            }

            return currentUserInfo;

        } catch (InstagramException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String processTwitterData(String token, String verifier) throws TwitterException, IOException {

        AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
        twitter.setOAuthAccessToken(accessToken);
        return "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h2>Success</h2>\n" +
                "\n" +
                "Click <button onclick=\"myFunction()\">here</button> to close this.\n" +
                "\n" +
                "<script>\n" +
                "function myFunction() {\n" +
                "    window.close();" +
                "}\n" +
                "</script>\n" +
                "\n" +
                "</body>\n" +
                "</html>";


    }

    public List<Review> getTwitterReviews(Product product) throws TwitterException {

        List<Review> reviews = new ArrayList<>();

        ResponseList<Status> userTimeline = twitter.getUserTimeline(twitter.getId(), new Paging(1, 100));
        String testword = product.getProductName();
        for (Status status : userTimeline) {
            if (status.getText().contains(testword)) {
                Review review = new Review();
                review.setReviewerName(status.getUser().getScreenName());
                review.setReviewContent(status.getText());
                review.setSourceName("Twitter");
                review.setCreatedAt(status.getCreatedAt());
                review.setReviewerThumbnailImgUrl(status.getUser().getProfileImageURL());
                review.setProductId(product.getId());
                reviews.add(review);
            }
        }
        return reviews;
    }


//PagableResponseList<User> friendsList = twitter.getFriendsList(twitter.getId(), -10);
        /*for (int i = 0; i < friendsList.size(); i++) {
            User user = friendsList.get(i);

            ResponseList<Status> userTimeline = twitter.getUserTimeline(user.getId(), new Paging(1, 10));
            String testword = product.getProductName();
            if (Objects.nonNull(userTimeline) && !userTimeline.isEmpty()) {
                for (Status status : userTimeline) {
                    if (status.getText().contains(testword)) {
                        Review review = new Review();
                        review.setReviewerName(user.getName());
                        review.setReviewContent(status.getText());
                        review.setSourceName("Twitter");
                        review.setCreatedAt(status.getCreatedAt());
                        review.setReviewerThumbnailImgUrl(user.getProfileImageURL());
                        review.setProductId(product.getId());
                        reviews.add(review);
                    }
                }
            }
        }*/

    public String getOAuth() throws TwitterException, IOException {

        if (Objects.isNull(twitter)) {
            twitter = TwitterFactory.getSingleton();
            twitter.setOAuthConsumer("73RkVGYmdjjTcKM8NAfZmC6SW", "8GIuHRiWgk7i7nd8KaweLOno5hN4ZH2QJAaUBvL3FnfAIziCEs");
            requestToken = twitter.getOAuthRequestToken();

        }
        return requestToken.getAuthorizationURL();

    }


}
