mindmap music (tag STAR, tag QUESTION) {
	- production (H) {
		description: "create new apps, libraries, and recordings"
	    view {
			color: "#0000FF"
		}
		- software (H) {
			- apps {
				-> granular_synth {
					* bruit_kit
				}
				-> grid_sequencer {
					* bruit_kit
				}
			}
			- libraries {
				-> bruit_kit <STAR>
			}
		}
		- releases {
			- artist_personas (M) {
				-> stories
				-> techniques
				-> aesthetics
				-> sensibilities
			}
			- visual_art
			- labels {
				- similar_artists
			}
		}
	}
	- consumption {
		- channels {
			- friends
			- online_radio
			- playlists
		}
		- new_music <STAR> <QUESTION> (L)
		- old_music # {
			-> my_music {
				-> undergraduate
				-> postgraduate {
					-> korea
				}
			}
		}
	}
}