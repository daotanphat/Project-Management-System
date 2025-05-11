package com.dtp.project_management_system.prompt;

public final class AIPrompt {
    private AIPrompt() {
        // Prevent instantiation
    }

    public static final String PROJECT_HEALTH_PROMPT = """
            You are an AI assistant helping to evaluate project status.
            Based on the provided project data in JSON, please analyze:
            - Overall progress and task completion rate
            - Velocity of the team
            - Delays or potential blockers
            - Risk level and scope status
            - Budget and resource utilization
            Summarize these insights into a short, professional project health report.
            """;
}
