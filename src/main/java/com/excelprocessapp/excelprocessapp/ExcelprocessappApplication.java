package com.excelprocessapp.excelprocessapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class ExcelprocessappApplication {
	// public static String matchedString(String regex, String description) {
	// 	Pattern pattern = Pattern.compile(regex);
	// 	Matcher matcher = pattern.matcher(description);
	// 	String matchString = "";
	// 	if (matcher.find()) {
	// 		matchString = matcher.group();
	// 		return matchString;
	// 	}
	// 	return null;
	// }

	public static void main(String[] args) {
		SpringApplication.run(ExcelprocessappApplication.class, args);
	}

	// @Bean
	// CommandLineRunner commandLineRunner(
	// 		ExcelProcessService service) {
	// 	return args -> {
	// 		List<MyExcelFile> myExcelFiles = service.readExcelFile(excelFilePath);

	// 		String polNumReg = "\\s[0-9]{10}";
	// 		String proPaperReg = "\\sHO\\w{12}";

	// 		for (String description : myExcelFiles.stream().map(excel -> excel.getDescription())
	// 				.collect(Collectors.toList())) {
	// 			String t1 = matchedString(polNumReg, description);

	// 			if (t1 != null) {
	// 				if (t1.trim().startsWith("0")) {
	// 					System.out.println("ProposalNum: " + t1.trim());
	// 				} else {
	// 					System.out.println("PolicyNum: " + t1.trim());
	// 				}
	// 			}
	// 			String t2 = matchedString(proPaperReg, description);
	// 			if (t2 != null) {
	// 				System.out.println("ProposalPaper: " + t2.trim());
	// 			}
	// 		}
	// 	};
	// }

}