# Algorithms Project: Optimal Routing for Self-Driving Cars  
## DriveBot Labs – CS Student Simulation

---

## 📌 Student Information

Please complete this section by the **end of the first week** of the semester.
Failure to do so may result in being dropped from the course due to inactivity, per university policy.

- **Full Name**: [Your Full Name Here]  
- **SFSU Email**: [Your SFSU Email Here]

---

## 🚗 Project Overview

You are a software engineer at **DriveBot Labs**, a startup building a smart routing engine for self-driving cars.
Think of this like a simplified version of what Waymo or Cruise might do, but your job is
to build the algorithms from scratch.

### 🧠 Your Mission

You’ll tackle the following challenges:

1. **Timely arrivals** — Compute the most efficient routes for vehicles to ensure passengers reach their destinations without delay.
2. **Fair pricing** — Calculate ride prices based on factors like demand, traffic, time of day, and availability of vehicles.
3. **Profitability** — Maximize the number of rides per day while minimizing downtime, and the number of times vehicles need to return to recharge.
4. **Avoid "stuck" situations** — Design systems that prevent cars from getting stuck due to roadblocks, detours, traffic congestion, or inefficient routing.
5. **Real-world constraint** — Vehicles operate in major California counties (e.g., San Francisco, San Mateo) but **cannot use freeways**.

> These challenges are dynamic, every decision may depend on variables like traffic conditions, time of day, and holidays, which can all affect routing, pricing, and energy usage. Your system must be adaptable and responsive to these real-world constraints.


---

## 🛠️ Project Philosophy

There is no single "correct" solution.
This project mirrors how real companies approach algorithm development: through iteration, testing,
and creative problem-solving.
Your job is to break down complex problems
and apply algorithms we've covered in class, **no outside algorithms allowed**.

Your ability to approach an ambiguous problem, design a plan,
test it, and improve it is far more valuable than just writing code.
In today’s industry, especially with the rise of AI boosting productivity,
companies are increasingly looking for strong problem-solvers, not just coders.
This project is your opportunity to build that mindset and practice those skills in a real-world scenario.

> Your grade will depend more on your thought process and problem-solving approach than on getting a "perfect" answer.


---

## ⚠️ AI Usage Policy

Unless explicitly approved by the instructor, **AI tools (e.g., ChatGPT, Copilot, Gemini, etc.) are not permitted** for any part of this project, **with one exception**:  
You may use AI to assist with the code implementation in **Milestone 4**, but only if the code is based on your own original pseudocode developed in Milestone 3.

Violations will be handled as follows:

- **First offense**: Zero on the milestone where AI was used.
- **Second offense**: F in the course, and a formal report will be filed with the university for academic dishonesty.

These policies are aligned with **SFSU’s Academic Integrity Policy**. We want this project to reflect your own thinking, problem-solving, and creativity, just like in a real job.

> While AI is widely used in the software industry, it is not a shortcut for doing the work; it is a tool to **enhance productivity** once you understand the problem.  
> To use AI effectively in real-world algorithmic challenges, you must first grasp the logic, structure, and reasoning behind the solution.  
> Building that foundation is what makes you a strong engineer and an effective problem-solver.


This is why AI-generated work is **not allowed** in this project in most of the cases.
Once you've mastered the underlying algorithmic thinking, you’ll be able to responsibly use AI in practice. For example, by turning your pseudocode into working code or quickly testing an idea. But you need to know how to solve the problem yourself first.

> In this course, AI-generated work can be identified quickly, often within seconds of reviewing a submission.  
> Don’t put your grade at risk. Do your own work and take pride in the solutions you create.  
> Remember, students are fully responsible for the authenticity of the work they submit.



---

## 🔍 Milestone 1: Problem Understanding & Brainstorming

### Objectives:
- Identify the problem’s goals and constraints.
- Brainstorm potential algorithmic approaches.
- Create a basic diagram or flowchart using [draw.io](https://draw.io) to visualize your thinking.

### Deliverables:
- `m1.pdf`: Describe the problem and brainstormed ideas. Use diagrams or flowcharts to support your explanation.

---

## ⚙️ Milestone 2: Brute-Force Approach & Complexity

### Objectives:
- Implement a basic brute-force version of your solution.
- Analyze the time and space complexity.
- Identify any performance bottlenecks.

### Deliverables:
- `m2.pdf`: Include the brute-force algorithm, complexity analysis, and a reflection on its limitations.

---

## 🧪 Milestone 3: Optimized Algorithm & Pseudocode

### Objectives:
- Design a more efficient algorithm using techniques such as graphs, greedy methods, or dynamic programming.
- Write clear pseudocode.
- Analyze the time and space complexity.
- Explain why it improves over the brute-force approach.

### Deliverables:
- `m3.pdf`: Include the optimized algorithm, pseudocode, and complexity analysis.

---

## 💻 Milestone 4: Code Implementation & Testing

For this milestone, you are allowed to use AI tools to generate your code and test cases, but only if they are based on your own pseudocode from Milestone 3.

This course emphasizes problem-solving and algorithm design over raw coding ability. The goal is to strengthen your understanding of how algorithms work and how to design effective solutions—skills that remain critical even in an AI-augmented industry.

> **Important:** If your code is AI-generated but not clearly derived from your original pseudocode, you will receive a **zero** for this milestone.  
> Your pseudocode should define a complete solution, typically involving **multiple functions or classes**, each corresponding to the objectives described in the project mission.

### Objectives:
- Implement the optimized algorithm in your preferred language (e.g., Python or Java).
- Include unit tests that handle edge cases such as high traffic, roadblocks, or lack of available cars.
- Ensure your code is clean, well-documented, and easy to follow.

### Deliverables:
- A link to your complete source code and test suite.
- `m4.pdf`: A report summarizing your testing process and results.  
  If you used AI tools to assist with code generation, include the exact prompts you used.



---

## 🎥 Milestone 5: Final Report & Demo

### Objectives:
- Summarize the entire project: problem, brute-force, optimization, complexity, and testing.
- Reflect on lessons learned and areas for improvement.
- Present your project clearly in a demo video.

### Deliverables:
- A **Zoom recording** link (must be recorded in the **cloud**, using your **SFSU Zoom account**).
- Students who **present live in class** do not need to submit a recording.
- Your final written report should be saved as `m5.pdf`.

---
> **Important:** For every milestone you submit, you must also include a link to your project repository in the Canvas submission.  
> This link is required in addition to the deliverables listed above.  
> If the link is missing, we won’t be able to access or grade your work, **no exceptions**.
---


## ✅ Grading Rubric (75% of Course Grade)

| Milestone       | Points | Extra Credit  | Description                                                                                                                     |
|-----------------|--------|---------------|---------------------------------------------------------------------------------------------------------------------------------|
| **Milestone 1**<br>Problem Understanding & Brainstorming | 10     | +1            | Clear explanation of the problem and constraints. Includes diagrams or flowcharts. <br>**EC** for outstanding clarity or creativity. |
| **Milestone 2**<br>Brute-Force Approach | 15     | +1            | Correct implementation, complexity analysis, and discussion of limitations. <br>**EC** for especially insightful critique.      |
| **Milestone 3**<br>Optimized Algorithm Design | 20     | +2            | Efficient algorithm, strong pseudocode, and clear reasoning. <br>**EC** for exceptional optimization.                           |
| **Milestone 4**<br>Implementation & Testing | 15     | +3            | Clean, documented code and robust tests. <br>**EC** for your implementing your own code (without AI).                           |
| **Milestone 5**<br>Final Report & Demo | 15     | +3            | Summary report and clear demo video. <br>**EC** for presenting live in class (limited slots).                                   |
| **Total**       | **75** | **Up to +10** | Final score before extra credit.                                                                                                |

---

## 💡 Tips for Success

- Start early—brainstorm and sketch your ideas.
- Think in graphs and pathfinding (e.g., the shortest path, congestion).
- Avoid external libraries to better understand the problem.
- Simulate different scenarios and test thoroughly.
- Be creative and take ownership of your approach. This is your chance to build something unique.

---

## 📅 Milestone Deadlines

Milestone deadlines are listed in the syllabus and will also be announced through:

- In-class reminders
- Email notifications
- Discord messages (on the course channel)

If you have any questions, don’t hesitate to ask during class, attend office hours, or post on the course Discord.

---

**Good luck, and have fun building the future of self-driving tech! 🚘**
