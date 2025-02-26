import "../../../styles/admin-style.css"
import OverviewCard from "./OverviewCard.jsx";
import LineChart from "./LineChart.jsx";
import DonutChart from "./DonutChart.jsx";
import BarChart from "./BarChart.jsx";

export default function AdminDashboardView() {
    return (<div className="admin-section">
        <h2 className="admin-title">Dash Board</h2>
        <div className="dashboard-container">
            {/* Overview 카드 */}
            <div className="overview-grid">
                <OverviewCard title="Views" value="7,265" change="+11.01%" color="blue-light" />
                <OverviewCard title="Visits" value="3,671" change="-0.03%" color="blue-medium" />
                <OverviewCard title="New Users" value="156" change="+15.03%" color="purple-light" />
            </div>

            {/* 차트 섹션 */}
            <div className="charts-container">
                <div className="chart-box">
                    <h3>Total Users</h3>
                    <LineChart />
                </div>
                <div className="chart-box">
                    <h3>GPT 사용량</h3>
                    <DonutChart />
                </div>
            </div>

            {/* 트래픽 바 차트 */}
            <div className="chart-box traffic-box">
                <h3>Traffic by Coin</h3>
                <BarChart />
            </div>
        </div>
    </div>)
}