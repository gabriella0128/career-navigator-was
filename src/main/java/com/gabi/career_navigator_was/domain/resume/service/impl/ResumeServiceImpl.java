package com.gabi.career_navigator_was.domain.resume.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.gabi.career_navigator_was.domain.resume.dto.base.CertificateDto;
import com.gabi.career_navigator_was.domain.resume.dto.base.EducationDto;
import com.gabi.career_navigator_was.domain.resume.dto.base.ExperienceDto;
import com.gabi.career_navigator_was.domain.resume.dto.base.LanguageDto;
import com.gabi.career_navigator_was.domain.resume.dto.base.ResumeDto;
import com.gabi.career_navigator_was.domain.resume.dto.base.SkillDto;
import com.gabi.career_navigator_was.domain.resume.dto.request.DetailResumeReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.InsertResumeReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.UpdateCertificateReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.UpdateEducationReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.UpdateExperienceReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.UpdateLanguageReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.UpdateSkillReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.UpdateSummaryReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.UpdateTitleReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.inner.CertificateReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.inner.EducationReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.inner.ExperienceReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.inner.LanguageReq;
import com.gabi.career_navigator_was.domain.resume.dto.request.inner.SkillReq;
import com.gabi.career_navigator_was.domain.resume.dto.response.DetailResumeRes;
import com.gabi.career_navigator_was.domain.resume.dto.response.inner.CertificateRes;
import com.gabi.career_navigator_was.domain.resume.dto.response.inner.EducationRes;
import com.gabi.career_navigator_was.domain.resume.dto.response.inner.ExperienceRes;
import com.gabi.career_navigator_was.domain.resume.dto.response.inner.LanguageRes;
import com.gabi.career_navigator_was.domain.resume.dto.response.inner.SkillRes;
import com.gabi.career_navigator_was.domain.resume.service.ResumeService;
import com.gabi.career_navigator_was.domain.resume.service.da.CertificateDataAccess;
import com.gabi.career_navigator_was.domain.resume.service.da.EducationDataAccess;
import com.gabi.career_navigator_was.domain.resume.service.da.ExperienceDataAccess;
import com.gabi.career_navigator_was.domain.resume.service.da.LanguageDataAccess;
import com.gabi.career_navigator_was.domain.resume.service.da.ResumeDataAccess;
import com.gabi.career_navigator_was.domain.resume.service.da.SkillDataAccess;
import com.gabi.career_navigator_was.domain.user.service.da.UserInfoDataAccess;
import com.gabi.career_navigator_was.global.code.CarnavCustomErrorCode;
import com.gabi.career_navigator_was.global.code.YnType;
import com.gabi.career_navigator_was.global.dto.CommonResponse;
import com.gabi.career_navigator_was.global.exception.CarnavCustomException;
import com.gabi.career_navigator_was.global.util.JwtTokenUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
	private final JwtTokenUtil jwtTokenUtil;
	private final UserInfoDataAccess userInfoDataAccess;
	private final ResumeDataAccess resumeDataAccess;
	private final EducationDataAccess educationDataAccess;
	private final ExperienceDataAccess experienceDataAccess;
	private final SkillDataAccess skillDataAccess;
	private final CertificateDataAccess certificateDataAccess;
	private final LanguageDataAccess languageDataAccess;

	@Override
	public CommonResponse<Void> insertResume(InsertResumeReq body) {
		Long userIdx = getCurrentUserIdx();

		YnType representYn = resumeDataAccess.existsByUserIdx(userIdx)
			? YnType.N
			: YnType.Y;

		ResumeDto savedResumeDto = resumeDataAccess.save(ResumeDto.builder()
			.userIdx(userIdx)
			.title(body.title())
			.summary(body.summary())
			.representYn(representYn)
			.build());

		Long resumeIdx = savedResumeDto.resumeIdx();

		// 각 섹션별 데이터 일괄 저장
		saveEducations(resumeIdx, body.educations());
		saveExperiences(resumeIdx, body.experiences());
		saveSkills(resumeIdx, body.skills());
		saveCertificates(resumeIdx, body.certificates());
		saveLanguages(resumeIdx, body.languages());

		return CommonResponse.success("이력서 등록 성공", null);
	}

	@Override
	public CommonResponse<DetailResumeRes> retrieveResume(DetailResumeReq body) {
		Long userIdx = getCurrentUserIdx();

		if (!Objects.isNull(body.resumeIdx())) {
			ResumeDto resume = findByIdAndCheckOwner(body.resumeIdx(), userIdx);
			return CommonResponse.success("이력서 조회 성공", buildDetailResume(resume));
		}
		List<ResumeDto> allResumes = resumeDataAccess.findAllByUserIdx(userIdx);

		if (allResumes.isEmpty()) {
			return CommonResponse.success("이력서 조회 성공", null);
		}
		ResumeDto represent = allResumes.stream()
			.filter(r -> YnType.Y.equals(r.representYn()))
			.findFirst()
			.orElse(null);

		if (Objects.isNull(represent)) {
			return CommonResponse.success("이력서 조회 성공", null);
		}
		return CommonResponse.success("이력서 조회 성공", buildDetailResume(represent));

	}

	@Override
	public CommonResponse<Void> updateTitle(UpdateTitleReq body) {
		Long userIdx = getCurrentUserIdx();
		ResumeDto resume = findByIdAndCheckOwner(body.resumeIdx(), userIdx);
		ResumeDto toBeSavedResume = resume.toBuilder().title(body.title()).build();
		resumeDataAccess.save(toBeSavedResume);
		return CommonResponse.success("이력서 제목 수정 성공", null);

	}

	@Override
	public CommonResponse<Void> updateSummary(UpdateSummaryReq body) {
		Long userIdx = getCurrentUserIdx();
		ResumeDto resume = findByIdAndCheckOwner(body.resumeIdx(), userIdx);
		ResumeDto toBeSavedResume = resume.toBuilder().summary(body.summary()).build();
		resumeDataAccess.save(toBeSavedResume);
		return CommonResponse.success("이력서 요약 수정 성공", null);
	}

	@Override
	public CommonResponse<Void> updateEducation(UpdateEducationReq body) {
		Long userIdx = getCurrentUserIdx();
		ResumeDto resume = findByIdAndCheckOwner(body.resumeIdx(), userIdx);

		List<EducationDto> existing = educationDataAccess.findAllByResumeIdx(resume.resumeIdx());
		Map<Long, EducationDto> existingById = existing.stream()
			.collect(Collectors.toMap(EducationDto::educationIdx, Function.identity()));

		List<EducationReq> incoming = Optional.ofNullable(body.educations())
			.orElseGet(Collections::emptyList);

		List<EducationReq> forInsert = incoming.stream()
			.filter(r -> Objects.isNull(r.educationIdx()))
			.toList();

		List<EducationReq> forUpdateReqs = incoming.stream()
			.filter(r -> !Objects.isNull(r.educationIdx()))
			.toList();

		Map<Long, Long> idCount = forUpdateReqs.stream()
			.map(EducationReq::educationIdx)
			.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		idCount.entrySet().stream()
			.filter(e -> e.getValue() > 1)
			.findAny()
			.ifPresent(e -> {
				throw new CarnavCustomException(
					CarnavCustomErrorCode.INVALID_PARAMETER
				);
			});

		List<EducationDto> inserts = forInsert.stream()
			.map(r -> EducationDto.builder()
				.resumeIdx(resume.resumeIdx())
				.schoolName(r.schoolName())
				.degree(r.degree())
				.major(r.major())
				.startDate(r.startDate())
				.endDate(r.endDate())
				.description(r.description())
				.build())
			.toList();

		List<EducationDto> updates = forUpdateReqs.stream()
			.map(r -> {
				Long id = r.educationIdx();
				EducationDto cur = existingById.get(id);
				if (Objects.isNull(cur)) {
					throw new CarnavCustomException(
						CarnavCustomErrorCode.NOT_FOUND_RESOURCE

					);
				}
				return cur.toBuilder()
					.schoolName(r.schoolName())
					.degree(r.degree())
					.major(r.major())
					.startDate(r.startDate())
					.endDate(r.endDate())
					.description(r.description())
					.build();
			})
			.toList();

		Set<Long> sentIds = forUpdateReqs.stream()
			.map(EducationReq::educationIdx)
			.collect(Collectors.toSet());

		List<EducationDto> deletes = existing.stream()
			.filter(e -> !sentIds.contains(e.educationIdx()))
			.map(e -> e.toBuilder().delYn(YnType.Y).build())
			.toList();

		List<EducationDto> batch = Stream.of(inserts, updates, deletes)
			.flatMap(List::stream)
			.toList();

		if (!batch.isEmpty()) {
			educationDataAccess.saveAll(batch);
		}

		return CommonResponse.success("이력서 교육 수정 성공", null);
	}

	@Override
	public CommonResponse<Void> updateExperience(UpdateExperienceReq body) {
		Long userIdx = getCurrentUserIdx();
		ResumeDto resume = findByIdAndCheckOwner(body.resumeIdx(), userIdx);
		List<ExperienceDto> existing = experienceDataAccess.findAllByResumeIdx(resume.resumeIdx());

		Map<Long, ExperienceDto> existingById = existing.stream()
			.collect(Collectors.toMap(ExperienceDto::experienceIdx, Function.identity()));

		List<ExperienceReq> incoming = Optional.ofNullable(body.experiences()).orElseGet(Collections::emptyList);

		List<ExperienceReq> forInsert = incoming.stream().filter(r -> Objects.isNull(r.experienceIdx())).toList();

		List<ExperienceReq> forUpdateReqs = incoming.stream().filter(r -> !Objects.isNull(r.experienceIdx())).toList();

		Map<Long, Long> idCount = forUpdateReqs.stream()
			.map(ExperienceReq::experienceIdx)
			.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		idCount.entrySet().stream()
			.filter(e -> e.getValue() > 1)
			.findAny()
			.ifPresent(e -> {
				throw new CarnavCustomException(
					CarnavCustomErrorCode.INVALID_PARAMETER
				);
			});


		List<ExperienceDto> inserts = forInsert.stream()
			.map(r -> ExperienceDto.builder()
				.resumeIdx(resume.resumeIdx())
				.companyName(r.companyName())
				.position(r.position())
				.startDate(r.startDate())
				.endDate(r.endDate())
				.description(r.description())
				.build())
			.toList();

		List<ExperienceDto> updates = forUpdateReqs.stream()
			.map(r -> {
				Long id = r.experienceIdx();
				ExperienceDto cur = existingById.get(id);
				if (Objects.isNull(cur)) {
					throw new CarnavCustomException(
						CarnavCustomErrorCode.NOT_FOUND_RESOURCE

					);
				}
				return cur.toBuilder()
					.companyName(r.companyName())
					.position(r.position())
					.startDate(r.startDate())
					.endDate(r.endDate())
					.description(r.description())
					.build();
			})
			.toList();

		Set<Long> sentIds = forUpdateReqs.stream()
			.map(ExperienceReq::experienceIdx)
			.collect(Collectors.toSet());

		List<ExperienceDto> deletes = existing.stream()
			.filter(e -> !sentIds.contains(e.experienceIdx()))
			.map(e -> e.toBuilder().delYn(YnType.Y).build())
			.toList();

		List<ExperienceDto> batch = Stream.of(inserts, updates, deletes)
			.flatMap(List::stream)
			.toList();

		if (!batch.isEmpty()) {
			experienceDataAccess.saveAll(batch);
		}

		return CommonResponse.success("이력서 경력 수정 성공", null);
	}
	@Override
	public CommonResponse<Void> updateSkill(UpdateSkillReq body) {
		Long userIdx = getCurrentUserIdx();
		ResumeDto resume = findByIdAndCheckOwner(body.resumeIdx(), userIdx);

		List<SkillDto> existing = skillDataAccess.findAllByResumeIdx(resume.resumeIdx());
		Map<Long, SkillDto> existingById = existing.stream()
			.collect(Collectors.toMap(SkillDto::skillIdx, Function.identity()));

		List<SkillReq> incoming = Optional.ofNullable(body.skills()).orElseGet(Collections::emptyList);

		List<SkillReq> forInsert = incoming.stream()
			.filter(r -> Objects.isNull(r.skillIdx()))
			.toList();

		List<SkillReq> forUpdateReqs = incoming.stream()
			.filter(r -> !Objects.isNull(r.skillIdx()))
			.toList();

		Map<Long, Long> idCount = forUpdateReqs.stream()
			.map(SkillReq::skillIdx)
			.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		idCount.entrySet().stream()
			.filter(e -> e.getValue() > 1)
			.findAny()
			.ifPresent(e -> { throw new CarnavCustomException(CarnavCustomErrorCode.INVALID_PARAMETER); });

		List<SkillDto> inserts = forInsert.stream()
			.map(r -> SkillDto.builder()
				.resumeIdx(resume.resumeIdx())
				.skillName(r.skillName())
				.proficiency(r.proficiency())
				.build())
			.toList();

		List<SkillDto> updates = forUpdateReqs.stream()
			.map(r -> {
				Long id = r.skillIdx();
				SkillDto cur = existingById.get(id);
				if (Objects.isNull(cur)) {
					throw new CarnavCustomException(CarnavCustomErrorCode.NOT_FOUND_RESOURCE);
				}
				return cur.toBuilder()
					.skillName(r.skillName())
					.proficiency(r.proficiency())
					.build();
			})
			.toList();

		Set<Long> sentIds = forUpdateReqs.stream()
			.map(SkillReq::skillIdx)
			.collect(Collectors.toSet());

		List<SkillDto> deletes = existing.stream()
			.filter(e -> !sentIds.contains(e.skillIdx()))
			.map(e -> e.toBuilder().delYn(YnType.Y).build())
			.toList();

		List<SkillDto> batch = Stream.of(inserts, updates, deletes)
			.flatMap(List::stream)
			.toList();

		if (!batch.isEmpty()) {
			skillDataAccess.saveAll(batch);
		}

		return CommonResponse.success("이력서 스킬 수정 성공", null);
	}

	@Override
	public CommonResponse<Void> updateCertificate(UpdateCertificateReq body) {
		Long userIdx = getCurrentUserIdx();
		ResumeDto resume = findByIdAndCheckOwner(body.resumeIdx(), userIdx);

		List<CertificateDto> existing = certificateDataAccess.findAllByResumeIdx(resume.resumeIdx());
		Map<Long, CertificateDto> existingById = existing.stream()
			.collect(Collectors.toMap(CertificateDto::certificateIdx, Function.identity()));

		List<CertificateReq> incoming = Optional.ofNullable(body.certificates()).orElseGet(Collections::emptyList);

		List<CertificateReq> forInsert = incoming.stream()
			.filter(r -> Objects.isNull(r.certificateIdx()))
			.toList();

		List<CertificateReq> forUpdateReqs = incoming.stream()
			.filter(r -> !Objects.isNull(r.certificateIdx()))
			.toList();

		Map<Long, Long> idCount = forUpdateReqs.stream()
			.map(CertificateReq::certificateIdx)
			.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		idCount.entrySet().stream()
			.filter(e -> e.getValue() > 1)
			.findAny()
			.ifPresent(e -> { throw new CarnavCustomException(CarnavCustomErrorCode.INVALID_PARAMETER); });

		List<CertificateDto> inserts = forInsert.stream()
			.map(r -> CertificateDto.builder()
				.resumeIdx(resume.resumeIdx())
				.certificateName(r.certificateName())
				.issuedBy(r.issuedBy())
				.issueDate(r.issueDate())
				.build())
			.toList();

		List<CertificateDto> updates = forUpdateReqs.stream()
			.map(r -> {
				Long id = r.certificateIdx();
				CertificateDto cur = existingById.get(id);
				if (Objects.isNull(cur)) {
					throw new CarnavCustomException(CarnavCustomErrorCode.NOT_FOUND_RESOURCE);
				}
				return cur.toBuilder()
					.certificateName(r.certificateName())
					.issuedBy(r.issuedBy())
					.issueDate(r.issueDate())
					.build();
			})
			.toList();

		Set<Long> sentIds = forUpdateReqs.stream()
			.map(CertificateReq::certificateIdx)
			.collect(Collectors.toSet());

		List<CertificateDto> deletes = existing.stream()
			.filter(e -> !sentIds.contains(e.certificateIdx()))
			.map(e -> e.toBuilder().delYn(YnType.Y).build())
			.toList();

		List<CertificateDto> batch = Stream.of(inserts, updates, deletes)
			.flatMap(List::stream)
			.toList();

		if (!batch.isEmpty()) {
			certificateDataAccess.saveAll(batch);
		}

		return CommonResponse.success("이력서 자격증 수정 성공", null);
	}

	@Override
	public CommonResponse<Void> updateLanguage(UpdateLanguageReq body) {
		Long userIdx = getCurrentUserIdx();
		ResumeDto resume = findByIdAndCheckOwner(body.resumeIdx(), userIdx);

		List<LanguageDto> existing = languageDataAccess.findAllByResumeIdx(resume.resumeIdx());
		Map<Long, LanguageDto> existingById = existing.stream()
			.collect(Collectors.toMap(LanguageDto::languageIdx, Function.identity()));

		List<LanguageReq> incoming = Optional.ofNullable(body.languages()).orElseGet(Collections::emptyList);

		List<LanguageReq> forInsert = incoming.stream()
			.filter(r -> Objects.isNull(r.languageIdx()))
			.toList();

		List<LanguageReq> forUpdateReqs = incoming.stream()
			.filter(r -> !Objects.isNull(r.languageIdx()))
			.toList();

		Map<Long, Long> idCount = forUpdateReqs.stream()
			.map(LanguageReq::languageIdx)
			.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		idCount.entrySet().stream()
			.filter(e -> e.getValue() > 1)
			.findAny()
			.ifPresent(e -> { throw new CarnavCustomException(CarnavCustomErrorCode.INVALID_PARAMETER); });

		List<LanguageDto> inserts = forInsert.stream()
			.map(r -> LanguageDto.builder()
				.resumeIdx(resume.resumeIdx())
				.languageName(r.languageName())
				.level(r.level())
				.testName(r.testName())
				.testScore(r.testScore())
				.build())
			.toList();

		List<LanguageDto> updates = forUpdateReqs.stream()
			.map(r -> {
				Long id = r.languageIdx();
				LanguageDto cur = existingById.get(id);
				if (Objects.isNull(cur)) {
					throw new CarnavCustomException(CarnavCustomErrorCode.NOT_FOUND_RESOURCE);
				}
				return cur.toBuilder()
					.languageName(r.languageName())
					.languageName(r.languageName())
					.level(r.level())
					.testName(r.testName())
					.testScore(r.testScore())
					.build();
			})
			.toList();

		Set<Long> sentIds = forUpdateReqs.stream()
			.map(LanguageReq::languageIdx)
			.collect(Collectors.toSet());

		List<LanguageDto> deletes = existing.stream()
			.filter(e -> !sentIds.contains(e.languageIdx()))
			.map(e -> e.toBuilder().delYn(YnType.Y).build())
			.toList();

		List<LanguageDto> batch = Stream.of(inserts, updates, deletes)
			.flatMap(List::stream)
			.toList();

		if (!batch.isEmpty()) {
			languageDataAccess.saveAll(batch);
		}

		return CommonResponse.success("이력서 어학 수정 성공", null);
	}

	// 사용자 식별
	private Long getCurrentUserIdx() {
		String userId = jwtTokenUtil.getUserIdFromToken();
		return userInfoDataAccess.findByUserId(userId)
			.orElseThrow(() -> new CarnavCustomException(CarnavCustomErrorCode.NOT_FOUND_USER))
			.userIdx();
	}

	// ID 기반 조회 + 소유자 검증
	private ResumeDto findByIdAndCheckOwner(Long resumeIdx, Long userIdx) {
		ResumeDto resume = resumeDataAccess.findByResumeIdx(resumeIdx)
			.orElseThrow(() -> new CarnavCustomException(CarnavCustomErrorCode.NOT_FOUND_RESOURCE));

		if (!Objects.equals(resume.userIdx(), userIdx)) {
			throw new CarnavCustomException(CarnavCustomErrorCode.NO_AUTHORIZATION_FOUND);
		}
		return resume;
	}

	// DetailResumeRes 생성
	@Override
	public DetailResumeRes buildDetailResume(ResumeDto r) {
		Long ridx = r.resumeIdx();
		return DetailResumeRes.builder()
			.resumeIdx(ridx)
			.title(r.title())
			.summary(r.summary())
			.educations(mapEducations(ridx))
			.experiences(mapExperiences(ridx))
			.skills(mapSkills(ridx))
			.certificates(mapCertificates(ridx))
			.languages(mapLanguages(ridx))
			.build();
	}


	private List<EducationRes> mapEducations(Long resumeIdx) {
		return educationDataAccess.findAllByResumeIdx(resumeIdx).stream()
			.map(e -> EducationRes.builder()
				.educationIdx(e.educationIdx())
				.schoolName(e.schoolName())
				.degree(e.degree())
				.major(e.major())
				.startDate(e.startDate())
				.endDate(e.endDate())
				.description(e.description())
				.build())
			.toList();
	}

	private List<ExperienceRes> mapExperiences(Long resumeIdx) {
		return experienceDataAccess.findAllByResumeIdx(resumeIdx).stream()
			.map(e -> ExperienceRes.builder()
				.experienceIdx(e.experienceIdx())
				.companyName(e.companyName())
				.position(e.position())
				.startDate(e.startDate())
				.endDate(e.endDate())
				.description(e.description())
				.build())
			.toList();
	}

	private List<SkillRes> mapSkills(Long resumeIdx) {
		return skillDataAccess.findAllByResumeIdx(resumeIdx).stream()
			.map(s -> SkillRes.builder()
				.skillIdx(s.skillIdx())
				.skillName(s.skillName())
				.proficiency(s.proficiency())
				.build())
			.toList();
	}

	private List<CertificateRes> mapCertificates(Long resumeIdx) {
		return certificateDataAccess.findAllByResumeIdx(resumeIdx).stream()
			.map(c -> CertificateRes.builder()
				.certificateIdx(c.certificateIdx())
				.certificateName(c.certificateName())
				.issuedBy(c.issuedBy())
				.issueDate(c.issueDate())
				.build())
			.toList();
	}

	private List<LanguageRes> mapLanguages(Long resumeIdx) {
		return languageDataAccess.findAllByResumeIdx(resumeIdx).stream()
			.map(l -> LanguageRes.builder()
				.languageIdx(l.languageIdx())
				.languageName(l.languageName())
				.level(l.level())
				.testName(l.testName())
				.testScore(l.testScore())
				.build())
			.toList();
	}
	private void saveEducations(Long resumeIdx, List<EducationReq> reqs) {
		if (reqs.isEmpty()) return;
		List<EducationDto> dtos = reqs.stream()
			.map(r -> EducationDto.builder()
				.resumeIdx(resumeIdx)
				.schoolName(r.schoolName())
				.degree(r.degree())
				.major(r.major())
				.startDate(r.startDate())
				.endDate(r.endDate())
				.description(r.description())
				.build())
			.toList();
		educationDataAccess.saveAll(dtos);
	}

	private void saveExperiences(Long resumeIdx, List<ExperienceReq> reqs) {
		if (reqs.isEmpty()) return;
		List<ExperienceDto> dtos = reqs.stream()
			.map(r -> ExperienceDto.builder()
				.resumeIdx(resumeIdx)
				.companyName(r.companyName())
				.position(r.position())
				.startDate(r.startDate())
				.endDate(r.endDate())
				.description(r.description())
				.build())
			.toList();
		experienceDataAccess.saveAll(dtos);
	}

	private void saveSkills(Long resumeIdx, List<SkillReq> reqs) {
		if (reqs.isEmpty()) return;
		List<SkillDto> dtos = reqs.stream()
			.map(r -> SkillDto.builder()
				.resumeIdx(resumeIdx)
				.skillName(r.skillName())
				.proficiency(r.proficiency())
				.build())
			.toList();
		skillDataAccess.saveAll(dtos);
	}

	private void saveCertificates(Long resumeIdx, List<CertificateReq> reqs) {
		if (reqs.isEmpty()) return;
		List<CertificateDto> dtos = reqs.stream()
			.map(r -> CertificateDto.builder()
				.resumeIdx(resumeIdx)
				.certificateName(r.certificateName())
				.issuedBy(r.issuedBy())
				.issueDate(r.issueDate())
				.build())
			.toList();
		certificateDataAccess.saveAll(dtos);
	}

	private void saveLanguages(Long resumeIdx, List<LanguageReq> reqs) {
		if (reqs.isEmpty()) return;
		List<LanguageDto> dtos = reqs.stream()
			.map(r -> LanguageDto.builder()
				.resumeIdx(resumeIdx)
				.languageName(r.languageName())
				.level(r.level())
				.testName(r.testName())
				.testScore(r.testScore())
				.build())
			.toList();
		languageDataAccess.saveAll(dtos);
	}
}
