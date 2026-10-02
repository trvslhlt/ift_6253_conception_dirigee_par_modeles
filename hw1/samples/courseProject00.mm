mindmap CourseProject (tag STAR, tag NIX) {
	- preplanning # {
		- brainstorm {
			- review_suggested_topics {
				-> map_to_course_materials
				-> gauge_classmate_interest
			}
			- find_opportunities_in_familiar_topics {
				-> map_course_material_to_news
				-> map_news_to_course_material {
					-> codebase_migration <STAR>
					-> tool_modernization
				}
			}
		}	
	}
	- planning (H) {
		- review_assignment {
			description: "https://www.iro.umontreal.ca/~syriani/courses/IFT6253-2026A/Project/projet.html"
		}
		- goal_definition #
		- tasks {
			-> delineation
			-> detailing
		}
		- strategy {
			- phases {
				- review_prior_art
				- consult_industry_expert <NIX>
			}
			- techniques
			- milestones {
				-> model_from_text
				-> model_to_text
				-> trivial_end_to_end_language_migration
				-> nontrivial_refactor
				-> nontrivial_language_migration
			} 
		}
		- technology_survey {
			-> identify_system_roles
			-> review_role_candidates
		}
	}
	- deliverables {
		- schedule (M) {
			- map_tasks_to_calendar {
				* tasks
			}
			-> milestone_1
			-> milestone_2
			-> submit_paper
		}
		- assets {
			* presentation
			- paper
		}
		- presentation {
			- slides
			- demo
			- practice
		}
	}
}