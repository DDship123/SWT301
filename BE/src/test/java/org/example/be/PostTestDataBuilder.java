package org.example.be;

import org.example.be.entity.Member;
import org.example.be.entity.Post;
import org.example.be.entity.PostImage;
import org.example.be.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostTestDataBuilder {
    private Post post;

    public PostTestDataBuilder() {
        this.post = new Post();
        // Set default values
        setDefaultValues();
    }

    private void setDefaultValues() {
        post.setTitle("Default Test Post Title");
        post.setDescription("Default test description");
        post.setStatus("PENDING");
        post.setPrice(new BigDecimal("1000000"));
        post.setCreatedAt(LocalDateTime.now());
    }

    public PostTestDataBuilder withTitle(String title) {
        post.setTitle(title);
        return this;
    }

    public PostTestDataBuilder withDescription(String description) {
        post.setDescription(description);
        return this;
    }

    public PostTestDataBuilder withStatus(String status) {
        post.setStatus(status);
        return this;
    }

    public PostTestDataBuilder withPrice(BigDecimal price) {
        post.setPrice(price);
        return this;
    }

    public PostTestDataBuilder withSeller(Member seller) {
        post.setSeller(seller);
        return this;
    }

    public PostTestDataBuilder withProduct(Product product) {
        post.setProduct(product);
        return this;
    }

    public PostTestDataBuilder withVehicleProduct() {
        Member defaultMember = MemberTestDataBuilder.createDefaultMember();
        Product product = ProductTestDataBuilder.createVehicleProduct(defaultMember);
        post.setProduct(product);
        post.setSeller(defaultMember);
        return this;
    }

    public PostTestDataBuilder withBatteryProduct() {
        Member defaultMember = MemberTestDataBuilder.createDefaultMember();
        Product product = ProductTestDataBuilder.createBatteryProduct(defaultMember);
        post.setProduct(product);
        post.setSeller(defaultMember);
        return this;
    }

    public PostTestDataBuilder withImages(List<String> imageUrls) {
        List<PostImage> postImages = new ArrayList<>();
        for (String url : imageUrls) {
            PostImage image = new PostImage();
            image.setImageUrl(url);
            image.setPost(post);
            postImages.add(image);
        }
        post.setPostImages(postImages);
        return this;
    }

    public Post build() {
        return post;
    }

    // Static factory methods for common scenarios
    public static Post createVehiclePost() {
        return new PostTestDataBuilder()
                .withTitle("Bán Tesla Model 3 2022")
                .withDescription("Xe Tesla Model 3 tình trạng tốt")
                .withStatus("APPROVED")
                .withPrice(new BigDecimal("1200000000"))
                .withVehicleProduct()
                .build();
    }

    public static Post createBatteryPost() {
        return new PostTestDataBuilder()
                .withTitle("Pin Lithium 48V Samsung")
                .withDescription("Pin Samsung chất lượng cao")
                .withStatus("PENDING")
                .withPrice(new BigDecimal("15000000"))
                .withBatteryProduct()
                .build();
    }
}
