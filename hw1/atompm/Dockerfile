# Built from the upstream AToMPM VM image (build it from
# https://github.com/AToMPM/atompm/tree/master/packaging/docker if missing).
FROM atompmvm:latest

# AToMPM's transformation engine breaks on Python 3.12+ (Pivots copy bug), so
# every rule match fails with "maximum recursion depth exceeded".
COPY patch_pivots.py /tmp/patch_pivots.py
RUN python3 /tmp/patch_pivots.py && rm /tmp/patch_pivots.py

EXPOSE 8124

CMD ["bash", "run_AToMPM_local.sh"]
