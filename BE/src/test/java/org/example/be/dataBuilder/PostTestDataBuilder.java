package org.example.be.dataBuilder;

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
                .withTitle("Bán Pin Samsung 48V")
                .withDescription("Pin Samsung dung lượng cao")
                .withStatus("APPROVED")
                .withPrice(new BigDecimal("5000000"))
                .withBatteryProduct()
                .build();
    }

    /**
     * Creates a post without any product (for testing edge cases)
     */
    public static Post createPostWithoutProduct() {
        Member defaultMember = MemberTestDataBuilder.createDefaultMember();
        return new PostTestDataBuilder()
                .withTitle("Bài đăng không có sản phẩm")
                .withDescription("Bài đăng để test trường hợp edge case")
                .withStatus("APPROVED")
                .withPrice(new BigDecimal("5000000"))
                .withSeller(defaultMember)
                // Không set product - để null để test edge case
                .build();
    }

    public static List<Post> createPostListByStatus(String status) {
        List<Post> posts = new ArrayList<>();

        // Create 3 PENDING posts
        posts.add(new PostTestDataBuilder()
                .withTitle("Bán xe máy Honda Winner X 2023")
                .withDescription("Xe Honda Winner X mới 98%, bảo dưỡng định kỳ")
                .withStatus("PENDING")
                .withPrice(new BigDecimal("45000000"))
                .withVehicleProduct()
                .build());

        posts.add(new PostTestDataBuilder()
                .withTitle("Pin xe điện 60V 20Ah")
                .withDescription("Pin xe điện chất lượng cao, sử dụng 6 tháng")
                .withStatus("PENDING")
                .withPrice(new BigDecimal("8000000"))
                .withBatteryProduct()
                .build());

        posts.add(new PostTestDataBuilder()
                .withTitle("Bán Yamaha Exciter 150 2022")
                .withDescription("Xe Yamaha Exciter 150 zin toàn bộ")
                .withStatus("PENDING")
                .withPrice(new BigDecimal("52000000"))
                .withVehicleProduct()
                .build());

        // Create 3 APPROVED posts
        posts.add(new PostTestDataBuilder()
                .withTitle("Tesla Model S 2021 Full Option")
                .withDescription("Tesla Model S full option, xe chính chủ")
                .withStatus("APPROVED")
                .withPrice(new BigDecimal("2800000000"))
                .withVehicleProduct()
                .build());

        posts.add(new PostTestDataBuilder()
                .withTitle("Pin Lithium 72V 32Ah Panasonic")
                .withDescription("Pin Panasonic chính hãng, BH 2 năm")
                .withStatus("APPROVED")
                .withPrice(new BigDecimal("18000000"))
                .withBatteryProduct()
                .build());

        posts.add(new PostTestDataBuilder()
                .withTitle("Honda SH 350i 2023")
                .withDescription("Honda SH 350i mới 100%, chưa đăng ký")
                .withStatus("APPROVED")
                .withPrice(new BigDecimal("165000000"))
                .withVehicleProduct()
                .build());

        // Create 3 REJECTED posts
        posts.add(new PostTestDataBuilder()
                .withTitle("Xe máy cũ không rõ nguồn gốc")
                .withDescription("Xe cũ giá rẻ")
                .withStatus("REJECTED")
                .withPrice(new BigDecimal("5000000"))
                .withVehicleProduct()
                .build());

        posts.add(new PostTestDataBuilder()
                .withTitle("Pin cũ không BH")
                .withDescription("Pin cũ sử dụng lâu")
                .withStatus("REJECTED")
                .withPrice(new BigDecimal("2000000"))
                .withBatteryProduct()
                .build());

        posts.add(new PostTestDataBuilder()
                .withTitle("Xe lỗi không khởi động được")
                .withDescription("Xe có vấn đề kỹ thuật")
                .withStatus("REJECTED")
                .withPrice(new BigDecimal("10000000"))
                .withVehicleProduct()
                .build());

        List<Post> filteredPosts = new ArrayList<>();
        for (Post post : posts) {
            if (post.getStatus().equals(status)) {
                filteredPosts.add(post);
            }
        }
        return filteredPosts;
    }
}
