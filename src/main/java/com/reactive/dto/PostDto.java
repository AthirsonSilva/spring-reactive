package com.reactive.dto;

import java.time.LocalDateTime;

// import javax.validation.constraints.Min;
// import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {

	private String id;

	// @NotEmpty
	// @Min(5)
	private String title;

	// @NotEmpty
	// @Min(5)
	private String content;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
